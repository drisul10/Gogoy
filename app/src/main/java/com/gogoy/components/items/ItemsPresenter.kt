package com.gogoy.components.items

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.data.models.ItemModel
import com.gogoy.utils.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.json.JSONException
import org.json.JSONObject

class ItemsPresenter(val view: ItemsContract.View, val context: Context) : ItemsContract.Presenter, HandleRequestAPI {

    init {
        view.presenter = this
    }

    override fun start() {
        view.onPresenterStart()
    }

    override fun onError(error: VolleyError, func: String, id: Any) {
        val errorMessage = getErrorMessageVolley(error, context)
        Sentry.getContext().user = UserBuilder()
            .setData(setParamsSentry(errorMessage, this, context, func))
            .build()
        Sentry.capture(setMessageSentry(this, errorMessage))

        view.progressBarHide(REQUEST_ITEMS)
        view.snackbarShow(errorMessage, Color.RED)
        view.snackbarDismissOn(5000)
    }

    override fun onResponse(response: JSONObject, func: String, id: Any) {
        try {
            if (response.getBoolean(Constant.API_RESPONSE_BODY_STATUS)) {
                when (id) {
                    REQUEST_ITEMS -> {
                        val itemsJsonArray = response.getJSONArray(Constant.API_RESPONSE_BODY_ITEM)
                        val collectionType = object : TypeToken<Collection<ItemModel>>() {}.type
                        val itemsGSon =
                            Gson().fromJson<Collection<ItemModel>>(itemsJsonArray.toString(), collectionType)

                        view.showItems(itemsGSon)
                        view.progressBarHide(REQUEST_ITEMS)
                    }
                }
            }
        } catch (e: JSONException) {
            Sentry.getContext().user = UserBuilder()
                .setData(setParamsSentry(e.toString(), this, context, func))
                .build()
            Sentry.capture(setMessageSentry(this, e.toString()))
        }
    }

    override fun requestItems(v: View, activity: FragmentActivity, url: String) {
        val thisFun = Throwable().stackTrace[0].methodName
        fragmentActivity = activity

        RequestAPI(
            context,
            url, {
                onResponse(it, thisFun, REQUEST_ITEMS)
            }, {
                onError(it, thisFun, REQUEST_ITEMS)
            }
        ).requestGet(
            arrayOf()
        )
    }

    override fun getCodeRequestItems(): Int {
        return REQUEST_ITEMS
    }

    companion object {
        private var fragmentActivity: FragmentActivity? = null
        private const val REQUEST_ITEMS = 1
    }
}