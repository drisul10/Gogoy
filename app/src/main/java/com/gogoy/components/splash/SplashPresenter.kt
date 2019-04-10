package com.gogoy.components.splash

import android.content.Context
import com.android.volley.VolleyError
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.*
import com.gogoy.utils.Constant.API_REQUEST_HEADER_NAME_AUTHORIZATION
import com.gogoy.utils.Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE
import com.gogoy.utils.Constant.API_REQUEST_PARAM_VALUE_AOX
import com.gogoy.utils.Constant.API_RESPONSE_BODY_CONTENT
import com.gogoy.utils.Constant.API_RESPONSE_BODY_STATUS
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_BADGE
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_DISPLAY_NAME
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_EMAIL
import com.gogoy.utils.Constant.API_RESPONSE_FIELD_PHONE
import com.gogoy.utils.Constant.UP_BADGE
import com.gogoy.utils.Constant.UP_DISPLAY_NAME
import com.gogoy.utils.Constant.UP_EMAIL
import com.gogoy.utils.Constant.UP_PHONE_NUMBER
import com.gogoy.utils.Constant.URL_USER_PROFILE
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.json.JSONException
import org.json.JSONObject

class SplashPresenter(val context: Context, val view: SplashContract.View) : SplashContract.Presenter,
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
    }

    override fun onResponse(response: JSONObject, func: String, id: Any) {
        try {
            if (response.getBoolean(API_RESPONSE_BODY_STATUS)) {
                when (id) {
                    REQUEST_DATA_USER -> {
                        val contentsJSONObject = response.getJSONObject(API_RESPONSE_BODY_CONTENT)
                        val sharedPref = SharedPref(context)
                        val hashMap: java.util.HashMap<String, String> = hashMapOf(
                            UP_DISPLAY_NAME to contentsJSONObject.getString(API_RESPONSE_FIELD_DISPLAY_NAME),
                            UP_EMAIL to contentsJSONObject.getString(API_RESPONSE_FIELD_EMAIL),
                            UP_PHONE_NUMBER to contentsJSONObject.getString(API_RESPONSE_FIELD_PHONE),
                            UP_BADGE to contentsJSONObject.getString(API_RESPONSE_FIELD_BADGE)
                        )

                        sharedPref.setDataUser(hashMap)
                        context.startActivity(context.intentFor<MainActivity>().newTask())
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

    companion object {
        private const val REQUEST_DATA_USER = 1
    }
}