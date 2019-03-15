package com.gogoy.components.auth.resetpassword

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.gogoy.R
import com.gogoy.components.auth.login.LoginActivity
import com.gogoy.utils.EndPoints
import com.gogoy.utils.VolleySingleton
import com.gogoy.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONException
import org.json.JSONObject

class ResetPasswordActivity : AppCompatActivity() {

    private val ui = ResetPasswordUI.instance()
    private lateinit var iToken: String
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        iToken = intent.getStringExtra("TOKEN")

        println("TOKEN NEW PASSWORD: $iToken")

        ui.btnProcess.onClick {
            hideKeyboard()
            val rootLayout: LinearLayout = find(R.id.ll_root)
            snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
            snackbar.show()

            process()
        }
    }

    private fun process() {
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_RESET_NEW_PASSWORD,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    when (obj.getBoolean(("status"))) {
                        true -> {
                            snackbar.setText(obj.getString("message"))
                            Handler().postDelayed({
                                snackbar.dismiss()
                                startActivity(intentFor<LoginActivity>().newTask().clearTask())
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
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Authorization"] = iToken
                headers["Content-Type"] = "application/x-www-form-urlencoded"

                return headers
            }

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["password"] = ui.etPassword.text.toString()
                params["confirm_password"] = ui.etConfirmPassword.text.toString()
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}