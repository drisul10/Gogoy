package com.gogoy.utils

import android.content.Context
import android.content.SharedPreferences
import com.gogoy.data.models.ItemCartModel
import com.gogoy.utils.Constant.UP_BADGE
import com.gogoy.utils.Constant.UP_DATA_ITEM
import com.gogoy.utils.Constant.UP_DATA_USER
import com.gogoy.utils.Constant.UP_DISPLAY_NAME
import com.gogoy.utils.Constant.UP_EMAIL
import com.gogoy.utils.Constant.UP_PHONE_NUMBER
import com.gogoy.utils.Constant.UP_TEST_DATA_ITEM
import com.gogoy.utils.Constant.UP_TOKEN_AUTH
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPref(context: Context) {
    private val prefsFile = "com.gogoy.prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFile, 0)
    private val editor = prefs.edit()
    val userDisplayName = getDataUser()[UP_DISPLAY_NAME]
    val userEmail = getDataUser()[UP_EMAIL]
    val userPhone = getDataUser()[UP_PHONE_NUMBER]
    val userBadge = getDataUser()[UP_BADGE]

    fun existItemId(idToFind: String): Boolean {
        val json = prefs.getString(UP_DATA_ITEM, null)

        return json!!.contains("\"id\":\"$idToFind\"")
    }

    fun getTokenAuth(): String? {
        return prefs.getString(UP_TOKEN_AUTH, null)
    }

    fun getDataItems(): ArrayList<ItemCartModel> {
        val gSon = Gson()
        val type = object : TypeToken<ArrayList<ItemCartModel>>() {}.type
        val json = prefs.getString(UP_DATA_ITEM, null)
        val hashMap: ArrayList<ItemCartModel>

        hashMap = if (!(json.isNullOrBlank())) {
            gSon.fromJson(json, type)
        } else {
            ArrayList()
        }

        return hashMap
    }

    private fun getDataUser(): HashMap<String, String> {
        val gSon = Gson()
        val type = object : TypeToken<HashMap<String, String>>() {}.type
        val json = prefs.getString(UP_DATA_USER, null)
        val list: HashMap<String, String>

        list = if (!(json.isNullOrBlank())) {
            gSon.fromJson(json, type)
        } else {
            HashMap()
        }

        return list
    }

    fun removeTokenAuth() {
        editor.remove(UP_TOKEN_AUTH).commit()
    }

    fun setDataItems(obj: ArrayList<ItemCartModel>) {
        editor.putString(UP_DATA_ITEM, Gson().toJson(obj))
        editor.apply()
    }

    fun cartGetItems(): MutableList<Pair<String, Int>> {
        val type = object : TypeToken<MutableList<Pair<String, Int>>>() {}.type
        val json = prefs.getString(UP_TEST_DATA_ITEM, null)
        val list: MutableList<Pair<String, Int>>

        list = if (!(json.isNullOrBlank())) {
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }

        return list
    }

    fun cartAddItem(pairs: Pair<String, Int>) {
        val items = cartGetItems()

        if (items.isNullOrEmpty() || !items.contains(pairs)) {
            items.add(pairs.first to pairs.second)
            editor.putString(Constant.UP_TEST_DATA_ITEM, Gson().toJson(items))
            editor.apply()
        }
    }

    fun cartAddAmountItem(pairs: Pair<String, Int>) {
        val items = cartGetItems()

        if (!items.isNullOrEmpty() && items.contains(pairs)) {
            val index = items.indexOf(pairs)
            items.removeAt(index)
            items.add(index, pairs.first to pairs.second + 1)
            editor.putString(Constant.UP_TEST_DATA_ITEM, Gson().toJson(items))
            editor.apply()
        }
    }

    fun cartReduceAmountItem(pairs: Pair<String, Int>) {
        val items = cartGetItems()

        if (!items.isNullOrEmpty() && items.contains(pairs)) {
            val index = items.indexOf(pairs)
            items.removeAt(index)

            if (pairs.second > 1) {
                items.add(index, pairs.first to pairs.second - 1)
            }

            editor.putString(Constant.UP_TEST_DATA_ITEM, Gson().toJson(items))
            editor.apply()
        }
    }

    fun cartAmount(id: String?): Int {
        val items = cartGetItems()
        var amount = 0
        items.forEach {
            if (it.first == id) {
                amount = it.second
            }
        }

        return amount
    }

    fun cartResetItem() {
        editor.remove(UP_TEST_DATA_ITEM).apply()
    }

    fun setDataUser(obj: HashMap<String, String>) {
        editor.putString(UP_DATA_USER, Gson().toJson(obj))
        editor.apply()
    }

    fun setTokenAuth(token: String) {
        editor.putString(UP_TOKEN_AUTH, token)
        editor.apply()
    }
}