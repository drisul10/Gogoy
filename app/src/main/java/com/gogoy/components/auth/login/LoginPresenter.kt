package com.gogoy.components.auth.login

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.*
import com.gogoy.utils.Constant.API_REQUEST_HEADER_NAME_AUTHORIZATION
import com.gogoy.utils.Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE
import com.gogoy.utils.Constant.API_REQUEST_PARAM_VALUE_AOX
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_BADGE
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_DEVICE_ID
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_DISPLAY_NAME
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_EMAIL
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_PASSWORD
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_PHONE
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_TOKEN
import com.gogoy.utils.Constant.UP_BADGE
import com.gogoy.utils.Constant.UP_DISPLAY_NAME
import com.gogoy.utils.Constant.UP_EMAIL
import com.gogoy.utils.Constant.UP_PHONE_NUMBER
import com.gogoy.utils.Constant.URL_AUTH_LOGIN
import com.gogoy.utils.Constant.URL_USER_PROFILE
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class LoginPresenter(val view: LoginContract.View, val context: Context) : LoginContract.Presenter, HandleRequestAPI {

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
                    REQUEST_DATA_USER -> {
                        val contentsJSONObject = response.getJSONObject(Constant.API_RESPONSE_BODY_CONTENT)
                        val sharedPref = SharedPref(context)
                        val hashMap: HashMap<String, String> = hashMapOf(
                            UP_DISPLAY_NAME to contentsJSONObject.getString(API_RESPONSE_FIELD_DISPLAY_NAME),
                            UP_EMAIL to contentsJSONObject.getString(API_RESPONSE_FIELD_EMAIL),
                            UP_PHONE_NUMBER to contentsJSONObject.getString(API_RESPONSE_FIELD_PHONE),
                            UP_BADGE to contentsJSONObject.getString(API_RESPONSE_FIELD_BADGE)
                        )

                        sharedPref.setDataUser(hashMap)
                    }

                    REQUEST_LOGIN -> {
                        val sharedPref = SharedPref(context)

                        sharedPref.setTokenAuth(response.getString(API_RESPONSE_FIELD_TOKEN))

                        requestDataUser(sharedPref.getTokenAuth() as String)

                        fragmentActivity?.startActivity(fragmentActivity?.intentFor<MainActivity>()?.newTask()?.clearTask())
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

    override fun requestDataUser(token: String) {
        val thisFun = Throwable().stackTrace[0].methodName

        RequestAPI(
            context,
            URL_USER_PROFILE, {
                onResponse(it, thisFun, REQUEST_DATA_USER)
            }, {
                onError(it, thisFun, REQUEST_DATA_USER)
            }
        ).requestGet(
            arrayOf(
                API_REQUEST_PARAM_NAME_CONTENT_TYPE to API_REQUEST_PARAM_VALUE_AOX
            ),
            arrayOf(
                API_REQUEST_HEADER_NAME_AUTHORIZATION to token
            )
        )
    }

    override fun requestLogin(v: View?, activity: FragmentActivity) {
        val etEmail = v?.find<EditText>(R.id.et_email)
        val etPaswd = v?.find<EditText>(R.id.et_password)
        val thisFun = Throwable().stackTrace[0].methodName
        fragmentActivity = activity

        RequestAPI(
            context,
            URL_AUTH_LOGIN, {
                onResponse(it, thisFun, REQUEST_LOGIN)
            }, {
                onError(it, thisFun, REQUEST_LOGIN)
            }
        ).requestPost(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX,
                API_RESPONSE_FIELD_EMAIL to etEmail?.text.toString(),
                API_RESPONSE_FIELD_PASSWORD to etPaswd?.text.toString(),
                API_RESPONSE_FIELD_DEVICE_ID to getDeviceId(context)
            )
        )
    }

    companion object {
        private var fragmentActivity: FragmentActivity? = null
        private const val REQUEST_DATA_USER = 1
        private const val REQUEST_LOGIN = 2
    }
}