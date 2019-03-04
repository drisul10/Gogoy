package com.gogoy.components.auth.login

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.gogoy.R
import com.gogoy.utils.EndPoints
import com.gogoy.utils.VolleySingleton
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.toast
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    val ui = LoginUI()
    private lateinit var etEmail: EditText
    private lateinit var etPaswd: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        ui.btnLogin.onClick {
            //            startActivity(intentFor<MainActivity>().newTask().clearTask())
//            overridePendingTransition(R.anim.right_in, R.anim.left_out)
            login()
        }
    }

    private fun login() {
        etEmail = find(R.id.et_email)
        etPaswd = find(R.id.et_password)

        println("DATA_EMAIL: ${etEmail.text}")
        println("DATA_PASWD: ${etPaswd.text}")

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_AUTH_LOGIN,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    toast(obj.getString("access_token"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                println("VOLLEY_ERROR: ${volleyError.message}")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                headers["X-Requested-With"] = "XMLHttpRequest"
                return headers
            }

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
//                params["Content-Type"] = "application/json"
//                params["Accept"] = "application/json"

                params["email"] = etEmail.text.toString()
                params["password"] = etPaswd.text.toString()
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}