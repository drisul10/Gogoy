package com.gogoy.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.Settings
import android.util.Base64
import com.android.volley.*
import com.gogoy.R
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.util.*

fun decodeImageBase64ToBitmap(encodedImage: String?): Bitmap {
    val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)

    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

fun encodeImageToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    val imgBytes = byteArrayOutputStream.toByteArray()

    return Base64.encodeToString(imgBytes, Base64.DEFAULT)
}

@SuppressLint("HardwareIds")
fun getDeviceId(context: Context): String {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}

fun setParamsSentry(
    error: String,
    instance: Any,
    context: Context,
    function: String,
    input: String = ""
): HashMap<String, Any> {
    val params = HashMap<String, Any>()
    params[Constant.UP_CLASS_QUALIFIED] = instance.javaClass.kotlin.qualifiedName.toString()
    params[Constant.UP_ERROR] = error
    params[Constant.UP_DEVICE] = getDeviceId(context)
    params[Constant.UP_FUNCTION] = function
    if (input.isEmpty()) {
        params[Constant.UP_INPUT] = 0
    } else {
        params[Constant.UP_INPUT] = input
    }

    params[Constant.UP_SDK_VERSION] = Build.VERSION.SDK_INT

    return params
}

fun setMessageSentry(instance: Any, error: String): String {
    return "${instance.javaClass.kotlin.simpleName} - $error"
}

fun getErrorMessageVolley(error: VolleyError, context: Context): String {
    var errorMessage = context.getString(R.string.error_unknown)

    if (error is TimeoutError || error is NoConnectionError) {
        errorMessage = context.getString(R.string.internet_problem)
    } else if (error is AuthFailureError) {
        errorMessage = try {
            val objResponse = JSONObject(String(error.networkResponse.data))
            objResponse.getString(Constant.API_RESPONSE_BODY_MESSAGE)
        } catch (e: Exception) {
            context.getString(R.string.error_custom, Constant.CODE_ERROR_UNAUTHORIZED)
        }
    } else if (error is ServerError) {
        errorMessage = try {
            val objResponse = JSONObject(String(error.networkResponse.data))
            objResponse.getString(Constant.API_RESPONSE_BODY_MESSAGE)
        } catch (e: Exception) {
            context.getString(R.string.error_custom, Constant.CODE_ERROR_BAD_REQUEST)
        }
    } else if (error is NetworkError) {
        errorMessage = context.getString(R.string.error_custom, Constant.CODE_ERROR_NETWORK)
    } else if (error is ParseError) {
        errorMessage = context.getString(R.string.error_custom, Constant.CODE_ERROR_PARSE)
    }

    return errorMessage
}

fun setUrl(typeId: Int, vararg pairs: Pair<Int, String>): String {
    var url = Constant.NULL

    loop@ for (i in pairs) {
        if (i.first == typeId) {
            url = i.second
            break@loop
        }
    }

    return url
}