package com.gogoy.components.auth.forgotPassword.resetPassword

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.components.auth.login.LoginActivity
import com.gogoy.utils.*
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.json.JSONException
import org.json.JSONObject

class ResetPasswordPresenter(val view: ResetPasswordContract.View, val context: Context) :
    ResetPasswordContract.Presenter, HandleRequestAPI {

    private lateinit var etPaswd: EditText
    private lateinit var etPaswdConfirm: EditText

    init {
        view.presenter = this
    }

    override fun start() {
        view.onPresenterStart()
    }

    override fun onError(error: VolleyError, func: String, id: Any) {
        val errorMessage = getErrorMessageVolley(error, context)
        Sentry.getContext().user = UserBuilder()
            .setData(setParamsSentry(errorMessage, this, context, func))
            .build()
        Sentry.capture(setMessageSentry(this, errorMessage))

        view.snackbarShow(errorMessage, Color.RED)
        view.snackbarDismissOn(5000)
    }

    override fun onResponse(response: JSONObject, func: String, id: Any) {
        try {
            if (response.getBoolean(Constant.API_RESPONSE_BODY_STATUS)) {
                when (id) {
                    REQUEST_RESET_PASSWORD -> {
                        fragmentActivity?.startActivity(fragmentActivity?.intentFor<LoginActivity>()?.newTask()?.clearTask())
                        fragmentActivity?.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                    }
                }
            }
        } catch (e: JSONException) {
            Sentry.getContext().user = UserBuilder()
                .setData(setParamsSentry(e.toString(), this, context, func))
                .build()
            Sentry.capture(setMessageSentry(this, e.toString()))
        }
    }

    override fun resetResetPassword(v: View, activity: FragmentActivity, token: String) {
        val thisFun = Throwable().stackTrace[0].methodName
        fragmentActivity = activity

        etPaswd = v.find(R.id.et_password) as EditText
        etPaswdConfirm = v.find(R.id.et_password_confirm) as EditText

        RequestAPI(
            context,
            Constant.URL_RESET_NEW_PASSWORD, {
                onResponse(it, thisFun, REQUEST_RESET_PASSWORD)
            }, {
                onError(it, thisFun, REQUEST_RESET_PASSWORD)
            }
        ).requestPost(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_PASSWORD to etPaswd.text.toString(),
                Constant.API_REQUEST_PARAM_NAME_PASSWORD_CONFIRM to etPaswdConfirm.text.toString()
            ),
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                Constant.API_REQUEST_HEADER_NAME_AUTHORIZATION to token
            )
        )
    }

    companion object {
        const val REQUEST_RESET_PASSWORD = 1
        private var fragmentActivity: FragmentActivity? = null
    }
}