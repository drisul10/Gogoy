package com.gogoy.components.auth.register

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.*
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RegisterPresenter(val view: RegisterContract.View, val context: Context) : RegisterContract.Presenter,
    HandleRequestAPI {

    private lateinit var etDisplayName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPaswd: EditText
    private lateinit var etPaswdConfirm: EditText
    private lateinit var etPhone: EditText

    init {
        view.presenter = this
    }

    override fun start() {
        view.onPresenterStart()
    }

    override fun onError(error: VolleyError, func: String, id: Any) {
        val errorMessage: String

        when (id) {
            REQUEST_REGISTER -> {
                val errorObj = JSONObject(String(error.networkResponse.data))
                val errorJSONObject = errorObj.getJSONObject(Constant.API_RESPONSE_BODY_ERROR)
                var errorJSONArray = JSONArray()

                when {
                    errorJSONObject.has("name")
                    -> errorJSONArray = errorJSONObject.getJSONArray("name")
                    errorJSONObject.has("email")
                    -> errorJSONArray = errorJSONObject.getJSONArray("email")
                    errorJSONObject.has("password")
                    -> errorJSONArray = errorJSONObject.getJSONArray("password")
                    errorJSONObject.has("confirm_password")
                    -> errorJSONArray = errorJSONObject.getJSONArray("confirm_password")
                    errorJSONObject.has("no_hp")
                    -> errorJSONArray = errorJSONObject.getJSONArray("no_hp")
                    else -> {
                        errorJSONArray.put(fragmentActivity?.getString(R.string.error_unknown))
                    }
                }

                errorMessage = errorJSONArray[0].toString()
            }

            else -> errorMessage = getErrorMessageVolley(error, context)
        }

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
                val sharedPref = SharedPref(context)

                when (id) {
                    REQUEST_DATA_USER -> {
                        val contentsJSONObject = response.getJSONObject(Constant.API_RESPONSE_BODY_CONTENT)
                        val hashMap: java.util.HashMap<String, String> = hashMapOf(
                            Constant.UP_DISPLAY_NAME to contentsJSONObject.getString(Constant.API_RESPONSE_FIELD_DISPLAY_NAME),
                            Constant.UP_EMAIL to contentsJSONObject.getString(Constant.API_RESPONSE_FIELD_EMAIL),
                            Constant.UP_PHONE_NUMBER to contentsJSONObject.getString(Constant.API_RESPONSE_FIELD_PHONE),
                            Constant.UP_BADGE to contentsJSONObject.getString(Constant.API_RESPONSE_FIELD_BADGE)
                        )

                        sharedPref.setDataUser(hashMap)
                    }

                    REQUEST_LOGIN -> {
                        sharedPref.setTokenAuth(response.getString(Constant.API_RESPONSE_FIELD_TOKEN))
                        requestDataUser(sharedPref.getTokenAuth() as String)

                        fragmentActivity?.startActivity(fragmentActivity?.intentFor<MainActivity>()?.newTask()?.clearTask())
                        fragmentActivity?.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                    }

                    REQUEST_REGISTER -> {
                        requestLogin(fragmentActivity)
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

    override fun requestLogin(activity: FragmentActivity?) {
        val thisFun = Throwable().stackTrace[0].methodName
        fragmentActivity = activity

        RequestAPI(
            context,
            Constant.URL_AUTH_LOGIN, {
                onResponse(it, thisFun, REQUEST_LOGIN)
            }, {
                onError(it, thisFun, REQUEST_LOGIN)
            }
        ).requestPost(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                Constant.API_RESPONSE_FIELD_EMAIL to etEmail.text.toString(),
                Constant.API_RESPONSE_FIELD_PASSWORD to etPaswd.text.toString(),
                Constant.API_RESPONSE_FIELD_DEVICE_ID to getDeviceId(context)
            )
        )
    }

    override fun requestDataUser(token: String) {
        val thisFun = Throwable().stackTrace[0].methodName

        RequestAPI(
            context,
            Constant.URL_USER_PROFILE, {
                onResponse(it, thisFun, REQUEST_DATA_USER)
            }, {
                onError(it, thisFun, REQUEST_DATA_USER)
            }
        ).requestGet(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX
            ),
            arrayOf(
                Constant.API_REQUEST_HEADER_NAME_AUTHORIZATION to token
            )
        )
    }

    override fun requestRegister(v: View) {
        val thisFun = Throwable().stackTrace[0].methodName

        etDisplayName = v.find(R.id.et_display_name) as EditText
        etEmail = v.find(R.id.et_email) as EditText
        etPaswd = v.find(R.id.et_password) as EditText
        etPaswdConfirm = v.find(R.id.et_password_confirm) as EditText
        etPhone = v.find(R.id.et_phone) as EditText

        RequestAPI(
            context,
            Constant.URL_AUTH_REGISTER, {
                onResponse(it, thisFun, REQUEST_REGISTER)
            }, {
                onError(it, thisFun, REQUEST_REGISTER)
            }
        ).requestGet(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                Constant.API_RESPONSE_FIELD_DISPLAY_NAME to etDisplayName.text.toString(),
                Constant.API_REQUEST_PARAM_NAME_EMAIL to etEmail.text.toString(),
                Constant.API_REQUEST_PARAM_NAME_PASSWORD to etPaswd.text.toString(),
                Constant.API_REQUEST_PARAM_NAME_PASSWORD_CONFIRM to etPaswdConfirm.text.toString(),
                Constant.API_REQUEST_PARAM_NAME_PHONE to etPhone.text.toString()
            )
        )
    }

    companion object {
        private const val REQUEST_DATA_USER = 1
        private const val REQUEST_LOGIN = 2
        private const val REQUEST_REGISTER = 3
        private var fragmentActivity: FragmentActivity? = null
    }
}