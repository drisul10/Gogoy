package com.gogoy.utils

import android.content.Context
import android.content.SharedPreferences
import com.gogoy.data.models.ItemCartModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs(context: Context) {
    private val prefsFile = "com.gogoy.prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFile, 0)
    private val editor = prefs.edit()

    fun setPref(obj: ArrayList<ItemCartModel>) {
        editor.putString(KEY_NAME, Gson().toJson(obj))
        editor.apply()
    }

    fun getPref(): ArrayList<ItemCartModel> {
        val gSon = Gson()
        val type = object : TypeToken<ArrayList<ItemCartModel>>() {}.type
        val json = prefs.getString(KEY_NAME, null)
        val list: ArrayList<ItemCartModel>

        list = try {
            gSon.fromJson(json, type)
        } catch (e: Exception) {
            ArrayList()
        }

        return list
    }

    fun existId(idToFind: String): Boolean {
        val json = prefs.getString(KEY_NAME, null)

        return json!!.contains("\"id\":\"$idToFind\"")
    }

    companion object {
        const val KEY_NAME = "ITEM_OBJECT"
    }
}