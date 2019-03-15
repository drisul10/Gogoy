package com.gogoy.components.auth.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.EndPoints
import com.gogoy.utils.Prefs
import com.gogoy.utils.VolleySingleton
import com.gogoy.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    val ui = LoginUI()
    private lateinit var etEmail: EditText
    private lateinit var etPaswd: EditText
    private lateinit var snackbar: Snackbar
    private lateinit var pref: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        ui.btnLogin.onClick {
            hideKeyboard()

            val rootLayout: LinearLayout = find(R.id.ll_root)
            snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
            snackbar.show()
            login()
        }

        pref = Prefs(this)
    }

    private fun login() {
        etEmail = find(R.id.et_email)
        etPaswd = find(R.id.et_password)
        @SuppressLint("HardwareIds")
        val deviceID = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_AUTH_LOGIN,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    when (obj.getBoolean(("status"))) {
                        true -> {
                            println("TOKEN_AUTH: ${obj.getString("api_token")}")
                            pref.setTokenAuth(obj.getString("api_token"))
                            getDataUser()

                            snackbar.setText(obj.getString("message"))
                            Handler().postDelayed({
                                snackbar.dismiss()
                                startActivity(intentFor<MainActivity>().newTask().clearTask())
                                overridePendingTransition(R.anim.right_in, R.anim.left_out)
                            }, 1500)
                        }
                    }
                } catch (e: JSONException) {
                    //TODO: send data
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                try {
                    val responseBody = String(error.networkResponse.data)
                    val obj = JSONObject(responseBody)

                    when (obj.getBoolean(("status"))) {
                        false -> {
                            val snackbarView = snackbar.view
                            snackbarView.setBackgroundColor(Color.RED)
                            snackbar.setText(obj.getString("message"))
                            Handler().postDelayed({
                                snackbar.dismiss()
                            }, 3000)
                        }
                    }
                } catch (e: JSONException) {
                    //TODO: send data
                    e.printStackTrace()
                }
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["email"] = etEmail.text.toString()
                params["password"] = etPaswd.text.toString()
                params["deviceId"] = deviceID
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
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