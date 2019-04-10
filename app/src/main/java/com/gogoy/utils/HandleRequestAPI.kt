package com.gogoy.utils

import com.android.volley.VolleyError
import org.json.JSONObject

interface HandleRequestAPI {
    fun onResponse(response: JSONObject, func: String, id: Any)
    fun onError(error: VolleyError, func: String, id: Any)
}