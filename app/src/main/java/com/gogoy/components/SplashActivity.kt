package com.gogoy.components

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.gogoy.components.auth.login.LoginActivity
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.EndPoints
import com.gogoy.utils.Prefs
import com.gogoy.utils.VolleySingleton
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity
import org.json.JSONException
import org.json.JSONObject

class SplashActivity : AppCompatActivity() {

    private lateinit var pref: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SplashUI().setContentView(this)

        pref = Prefs(this)

        //TODO: splash screen only when first open, pass when connect internet
        Handler().postDelayed({
            if (pref.getTokenAuth() != null) {
                getDataUser()
                startActivity<MainActivity>()
            } else {
                startActivity<LoginActivity>()
            }
            finish()
        }, 2000)
    }

    private fun getDataUser() {
        val token = pref.getTokenAuth()

        val stringRequest = object : StringRequest(
            Request.Method.GET,
            EndPoints.URL_USER_PROFILE,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    when (obj.getBoolean(("status"))) {
                        true -> {
                            val contentsJSONObject = obj.getJSONObject("contents")
                            val contentName = contentsJSONObject.getString("name")
                            val contentEmail = contentsJSONObject.getString("email")
                            val contentPhone = contentsJSONObject.getString("no_hp")

                            val hashMap: HashMap<String, String> = hashMapOf(
                                Prefs.USER_DISPLAY_NAME to contentName,
                                Prefs.USER_EMAIL to contentEmail,
                                Prefs.USER_PHONE to contentPhone
                            )

                            pref.setDataUser(hashMap)
                        }
                    }
                } catch (e: JSONException) {
                    //TODO: Send data
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                try {
                    val responseBody = String(error.networkResponse.data)
                    val obj = JSONObject(responseBody)

                    when (obj.getBoolean(("status"))) {
                        false -> {
                            //TODO
                        }
                    }
                } catch (e: JSONException) {
                    //TODO: Send data
                    e.printStackTrace()
                }
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = token.toString()
                headers["Content-Type"] = "application/x-www-form-urlencoded"
                return headers
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}