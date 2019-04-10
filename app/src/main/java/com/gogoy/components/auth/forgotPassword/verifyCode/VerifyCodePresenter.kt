package com.gogoy.components.auth.forgotPassword.verifyCode

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.components.auth.forgotPassword.resetPassword.ResetPasswordActivity
import com.gogoy.utils.*
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.json.JSONException
import org.json.JSONObject

class VerifyCodePresenter(val view: VerifyCodeContract.View, val context: Context) : VerifyCodeContract.Presenter,
    HandleRequestAPI {

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
                    REQUEST_RESEND_CODE -> {
                        view.showCountDown()
                        view.snackbarChange(response.getString(Constant.API_RESPONSE_BODY_MESSAGE), Color.BLACK)
                        view.snackbarDismissOn(5000)
                    }

                    REQUEST_VALIDATE_CODE -> {
                        fragmentActivity?.startActivity(
                            fragmentActivity?.intentFor<ResetPasswordActivity>(
                                Constant.UP_TOKEN to response.getString(Constant.API_RESPONSE_FIELD_TOKEN)
                            )?.newTask()?.clearTask()
                        )
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

    override fun requestResendCode(strEmail: String) {
        val thisFun = Throwable().stackTrace[0].methodName

        RequestAPI(
            context,
            Constant.URL_RESET_SEND_CODE, {
                onResponse(it, thisFun, REQUEST_RESEND_CODE)
            }, {
                onError(it, thisFun, REQUEST_RESEND_CODE)
            },
            true
        ).requestPost(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                Constant.API_REQUEST_PARAM_NAME_EMAIL to strEmail
            )
        )
    }

    override fun requestValidateCode(activity: FragmentActivity, strOtpCode: String, strEmail: String) {
        val thisFun = Throwable().stackTrace[0].methodName
        fragmentActivity = activity

        RequestAPI(
            context,
            Constant.URL_RESET_VERIFY_CODE, {
                onResponse(it, thisFun, REQUEST_VALIDATE_CODE)
            }, {
                onError(it, thisFun, REQUEST_VALIDATE_CODE)
            }
        ).requestPost(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                Constant.API_REQUEST_PARAM_NAME_OTP to strOtpCode
            ),
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                Constant.API_REQUEST_HEADER_NAME_AUTHORIZATION to strEmail
            )
        )
    }

    companion object {
        private var fragmentActivity: FragmentActivity? = null
        private const val REQUEST_RESEND_CODE = 1
        private const val REQUEST_VALIDATE_CODE = 2
    }
}