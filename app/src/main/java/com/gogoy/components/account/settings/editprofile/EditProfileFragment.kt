package com.gogoy.components.account.settings.editprofile

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.gogoy.R
import com.gogoy.utils.EndPoints
import com.gogoy.utils.Prefs
import com.gogoy.utils.VolleySingleton
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.find
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream

class EditProfileFragment : Fragment(), EditProfileContract.View {

    val ui = EditProfileFragmentUI.instance()
    override lateinit var presenter: EditProfileContract.Presenter
    private lateinit var snackbar: Snackbar
    private lateinit var pref: Prefs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == 1) {
            val imgUri: Uri? = data!!.data
            val imgPath = imgUri.toString()

            Glide.with(requireContext()).load(imgPath).into(ui.ivBadge)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()

        pref = Prefs(requireContext())

        ui.etDisplayName.setText(pref.userDisplayName)
        ui.etPhone.setText(pref.userPhone)

        ui.btnBrowse.onClick {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            if (intent.resolveActivity(activity!!.packageManager) != null) {
                startActivityForResult(intent, 1)
            }
        }

        ui.btnSubmit.onClick {
            val rootLayout: LinearLayout = find(R.id.ll_root)
            snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
            snackbar.show()

            process()
        }
    }

    private fun process() {
        val etName = find<EditText>(R.id.et_display_name)
        val etPhone = find<EditText>(R.id.et_phone)
        val pref = Prefs(requireContext())
        val token = pref.getTokenAuth()

        val stringRequest = object : StringRequest(
            Request.Method.POST,
            EndPoints.URL_USER_EDIT_PROFILE,
            Response.Listener { response ->
                try {
                    val obj = JSONObject(response)
                    when (obj.getBoolean(("status"))) {
                        true -> {
                            val hashMap: HashMap<String, String> = hashMapOf(
                                Prefs.USER_DISPLAY_NAME to etName.text.toString(),
                                Prefs.USER_EMAIL to pref.userEmail.toString(),
                                Prefs.USER_PHONE to etPhone.text.toString()
                            )
                            pref.setDataUser(hashMap)

                            snackbar.setText(obj.getString("message"))

                            Handler().postDelayed({
                                snackbar.dismiss()
                            }, 3000)
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
                            val errorJSONObject = obj.getJSONObject("errors")
                            var errorJSONArray = JSONArray()
                            val snackbarView = snackbar.view

                            when {
                                errorJSONObject.has("name")
                                -> errorJSONArray = errorJSONObject.getJSONArray("name")
                                errorJSONObject.has("no_hp")
                                -> errorJSONArray = errorJSONObject.getJSONArray("no_hp")
                            }

                            snackbarView.setBackgroundColor(Color.RED)
                            snackbar.setText(errorJSONArray[0].toString())
                            Handler().postDelayed({
                                snackbar.dismiss()
                            }, 3000)
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

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = etName.text.toString()

                if (etPhone.text.toString() != pref.userPhone) {
                    params["no_hp"] = etPhone.text.toString()
                } else {
                    params["no_hp"] = ""
                }

                params["foto"] = ""

                return params
            }
        }

        //adding request to queue
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    fun getStringImage(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imgBytes = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(imgBytes, Base64.DEFAULT)
    }

    override fun showForm() {}

    companion object {
        fun newInstance() = EditProfileFragment()
    }
}