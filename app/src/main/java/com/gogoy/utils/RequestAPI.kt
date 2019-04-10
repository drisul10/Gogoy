package com.gogoy.utils

import android.content.Context
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RequestAPI(
    val context: Context,
    val url: String,
    val result: (JSONObject) -> Unit,
    val error: (VolleyError) -> Unit,
    private val isSetRetryPolicy: Boolean = false
) {

    val volley: RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

    fun requestGet(params: Array<Pair<String, Any>>, headers: Array<Pair<String, Any>> = arrayOf()) {
        val hashMapHeaders = HashMap<String, String>()
        val hashMapParams = HashMap<String, String>()

        headers.forEach {
            hashMapHeaders[it.first] = it.second.toString()
        }

        params.forEach {
            hashMapParams[it.first] = it.second.toString()
        }

        makeRequest(Request.Method.GET, hashMapHeaders, hashMapParams)
    }

    fun requestPost(params: Array<Pair<String, Any>>, headers: Array<Pair<String, Any>> = arrayOf()) {
        val hashMapHeaders = HashMap<String, String>()
        val hashMapParams = HashMap<String, String>()

        headers.forEach {
            hashMapHeaders[it.first] = it.second.toString()
        }

        params.forEach {
            hashMapParams[it.first] = it.second.toString()
        }
        makeRequest(Request.Method.POST, hashMapHeaders, hashMapParams)
    }

    private fun makeRequest(method: Int, headers: HashMap<String, String>, params: HashMap<String, String>) {
        val stringRequest = object : StringRequest(method, url, { res ->
            result(JSONObject(res.toString().trim()))
        }, { volleyError ->
            error(volleyError)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                return headers
            }

            override fun getParams(): MutableMap<String, String> {
                return params
            }
        }

        if (isSetRetryPolicy)
            stringRequest.retryPolicy = DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )

        volley.add(stringRequest)
    }

}