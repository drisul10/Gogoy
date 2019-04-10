package com.gogoy.components.auth.forgotPassword.sendCode

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.components.auth.forgotPassword.verifyCode.VerifyCodeActivity
import com.gogoy.utils.*
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.json.JSONException
import org.json.JSONObject

class SendCodePresenter(val view: SendCodeContract.View, val context: Context) : SendCodeContract.Presenter,
    HandleRequestAPI {

    private lateinit var etEmail: EditText

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
                    REQUEST_SEND_CODE -> {
                        fragmentActivity?.startActivity(fragmentActivity?.intentFor<VerifyCodeActivity>(Constant.UP_EMAIL to etEmail.text.toString())?.newTask()?.clearTask())
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

    override fun sendCodeToEmail(v: View, activity: FragmentActivity) {
        val thisFun = Throwable().stackTrace[0].methodName
        etEmail = v.find(R.id.et_email)
        fragmentActivity = activity

        RequestAPI(
            context,
            Constant.URL_RESET_SEND_CODE, {
                onResponse(it, thisFun, REQUEST_SEND_CODE)
            }, {
                onError(it, thisFun, REQUEST_SEND_CODE)
            },
            true
        ).requestPost(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                Constant.API_REQUEST_PARAM_NAME_EMAIL to etEmail.text.toString()
            )
        )
    }

    companion object {
        private var fragmentActivity: FragmentActivity? = null
        const val REQUEST_SEND_CODE = 1
    }
}