package com.gogoy.components.auth.register

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

class RegisterActivity : AppCompatActivity() {

    val ui = RegisterUI()

    private lateinit var etDisplayName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPhone: EditText
    private lateinit var etPaswd: EditText
    private lateinit var etPaswdConfirm: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)

        ui.btnRegister.onClick {
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
            EndPoints.URL_AUTH_SIGNUP,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    toast(obj.getString("message"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                println("VOLLEY_ERROR: ${volleyError.message}")
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["name"] = etDisplayName.text.toString()
                params["email"] = etEmail.text.toString()
                params["password"] = etPaswd.text.toString()
                params["password_confirmation"] = etPaswdConfirm.text.toString()
                params["no_hp"] = etPhone.text.toString()
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}