package com.gogoy.components.account.settings

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.gogoy.R
import com.gogoy.components.account.settings.editprofile.EditProfileActivity
import com.gogoy.components.auth.login.LoginActivity
import com.gogoy.data.models.SettingsMenuModel
import com.gogoy.utils.EndPoints
import com.gogoy.utils.Prefs
import com.gogoy.utils.VolleySingleton
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.json.JSONException
import org.json.JSONObject

class SettingsMenuAdapter(
    private val context: Context,
    private var list: ArrayList<SettingsMenuModel> = arrayListOf()
) : RecyclerView.Adapter<SettingsMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SettingsMenuAdapter.PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvName.text = item.name

        holder.container.onClick {
            val activity = context as Activity

            when (item.id) {
                "EDIT_PROFILE" -> {
                    activity.startActivity<EditProfileActivity>()
                }

                "LOGOUT" -> {
                    val pref = Prefs(context)
                    val token = pref.getTokenAuth()

                    val stringRequest = object : StringRequest(
                        Request.Method.GET,
                        EndPoints.URL_AUTH_LOGOUT,
                        Response.Listener { response ->
                            try {
                                val obj = JSONObject(response)
                                when (obj.getBoolean(("status"))) {
                                    true -> {
                                        pref.removeTokenAuth()
                                        Handler().postDelayed({
                                            activity.startActivity(activity.intentFor<LoginActivity>().newTask().clearTask())
                                            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                                        }, 1500)
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
                                        Handler().postDelayed({
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
                    }

                    //adding request to queue
                    VolleySingleton.instance?.addToRequestQueue(stringRequest)
                }
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_menu_name)
        var container: LinearLayout = view.find(R.id.container)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(15)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.container
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL

                    textView {
                        id = R.id.tv_menu_name
                        textSize = 16f
                        textColorResource = R.color.colorText
                    }
                }
            }
        }
    }
}