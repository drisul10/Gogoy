package com.gogoy.components.auth.register

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
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
import com.gogoy.utils.VolleySingleton
import com.gogoy.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    val ui = RegisterUI()
    private lateinit var etDisplayName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPaswd: EditText
    private lateinit var etPaswdConfirm: EditText
    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)

        ui.btnRegister.onClick {
            hideKeyboard()

            val rootLayout: LinearLayout = find(R.id.ll_root)
            snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
            snackbar.show()
            register()
        }
    }

    private fun register() {
        etDisplayName = find(R.id.et_display_name)
        etEmail = find(R.id.et_email)
        etPhone = find(R.id.et_phone)
        etPaswd = find(R.id.et_password)
        etPaswdConfirm = find(R.id.et_password_confirm)

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_AUTH_REGISTER,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    when (obj.getBoolean(("status"))) {
                        true -> {
                            snackbar.setText(obj.getString("message"))
                            Handler().postDelayed({
                                snackbar.dismiss()
                                startActivity(intentFor<MainActivity>().clearTask().newTask())
                            }, 3000)
                        }
                    }
                } catch (e: JSONException) {
                    //TODO: send error
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                try {
                    val responseBody = String(error.networkResponse.data)
                    val obj = JSONObject(responseBody)

                    when (obj.getBoolean(("status"))) {
                        false -> {
                            val errorJSONObject = obj.getJSONObject("errors")
                            var errorJSONArray = JSONArray()
                            val snackbarView = snackbar.view

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
                                    //TODO: send data
                                    errorJSONArray.put("${R.string.error_unknown}. Code: REG")
                                }
                            }

                            snackbarView.setBackgroundColor(Color.RED)
                            snackbar.setText(errorJSONArray[0].toString())
                            Handler().postDelayed({
                                snackbar.dismiss()
                            }, 3000)
                        }
                    }
                } catch (e: JSONException) {
                    //TODO: send error
                    e.printStackTrace()
                }
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["name"] = etDisplayName.text.toString()
                params["email"] = etEmail.text.toString()
                params["password"] = etPaswd.text.toString()
                params["confirm_password"] = etPaswdConfirm.text.toString()
                params["no_hp"] = etPhone.text.toString()
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}