package com.gogoy.components.auth.resetpassword

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.gogoy.R
import com.gogoy.utils.*
import com.google.android.material.snackbar.Snackbar
import com.mukesh.OtpView
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.find
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import java.util.concurrent.TimeUnit

class VerifyCodeActivity : AppCompatActivity() {

    private lateinit var snackbar: Snackbar
    private lateinit var iEmail: String
    private lateinit var tvCountDown: TextView
    private lateinit var tvMessage: TextView
    private lateinit var ovCode: OtpView
    private var code: String = ""
    private lateinit var btnResend: Button
    private lateinit var btnValidate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_verify_code_activity)

        iEmail = intent.getStringExtra("EMAIL")

        ovCode = find(R.id.ov_code)
        tvMessage = find(R.id.tv_message)
        tvCountDown = find(R.id.tv_count_down)
        btnValidate = find(R.id.btn_validate)
        btnResend = find(R.id.btn_resend)

        tvMessage.text = applicationContext.getString(
            R.string.verivy_code_message,
            iEmail.replace("(^[^@]{3}|(?!^)\\G)[^@]".toRegex(), "$1*")
        )
        btnResend.gone()

        showCountDown()

        ovCode.setOtpCompletionListener { otp ->
            hideKeyboard()

            code = otp
            val rootLayout: LinearLayout = find(R.id.ll_root)
            snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
            snackbar.show()
            process()
        }

        btnResend.onClick {
            val rootLayout: LinearLayout = find(R.id.ll_root)
            snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
            snackbar.show()
            resendCode()
        }

        btnValidate.onClick {
            hideKeyboard()

            val rootLayout: LinearLayout = find(R.id.ll_root)
            snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
            snackbar.show()

            if (code.isEmpty()) {
                snackbar.setText(R.string.fill_code)

                Handler().postDelayed({
                    snackbar.dismiss()
                }, 1500)
            } else {
                process()
            }
        }
    }

    private fun showCountDown() {
        tvCountDown.visible()

        object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvCountDown.text = String.format(
                    "%d menit, %d detik",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                )
            }

            override fun onFinish() {
                tvCountDown.gone()
                btnResend.visible()
            }
        }.start()
    }

    private fun process() {
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_RESET_VERIFY_CODE,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    when (obj.getBoolean(("status"))) {
                        true -> {
                            snackbar.setText(obj.getString("message"))
                            Handler().postDelayed({
                                snackbar.dismiss()
                                startActivity(intentFor<ResetPasswordActivity>("TOKEN" to obj.getString("api_token")).newTask().clearTask())
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
                            tvCountDown.gone()
                            btnResend.visible()

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
                headers["Authorization"] = iEmail
                headers["Content-Type"] = "application/x-www-form-urlencoded"

                return headers
            }

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["token"] = code
                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun resendCode() {
        val stringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_RESET_SEND_CODE,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    when (obj.getBoolean(("status"))) {
                        true -> {
                            showCountDown()
                            snackbar.setText(obj.getString("message"))
                            Handler().postDelayed({
                                snackbar.dismiss()
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
                params["email"] = iEmail
                return params
            }
        }

        //prevent duplicate post
        stringRequest.retryPolicy = DefaultRetryPolicy(
            DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}