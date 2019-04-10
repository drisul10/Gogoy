package com.gogoy.components.item

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.*
import com.gogoy.utils.Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE
import com.gogoy.utils.Constant.API_REQUEST_PARAM_VALUE_AOX
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.json.JSONException
import org.json.JSONObject

class ItemPresenter(val view: ItemContract.View, val context: Context) : ItemContract.Presenter, HandleRequestAPI {

    init {
        view.presenter = this
    }

    override fun start() {
        view.onPresenterStart()
    }

    override fun onError(error: VolleyError, func: String, id: Any) {
        //TODO
    }

    override fun onResponse(response: JSONObject, func: String, id: Any) {
        try {
            if (response.getBoolean(Constant.API_RESPONSE_BODY_STATUS)) {
                when (id) {
                    REQUEST_ITEM -> {
                        val itemJSONObject = response.getJSONObject(Constant.API_RESPONSE_BODY_ITEMS)
                        val itemId = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_ID)
                        val itemName = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_ITEM_NAME)
                        val itemPrice = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_PRICE)
                        val itemDimension = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_DIMENSION)
                        val itemUnit = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_UNIT)
                        val itemDesc = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_DESC)
                        val itemInfo = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_INFO)
                        val itemStock = "itemJSONObject.getString(Constant.API_RESPONSE_FIELD_STOCK)"
                        val itemBadge = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_BADGE)
                        val itemCategory = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_CATEGORY)
                        val itemOwnerId = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_OWNER_ID)
                        val itemStatus = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_STATUS)
                        val itemUpdatedAt = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_UPDATED_AT)
                        val itemCreatedAt = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_CREATED_AT)
                        val itemPriceDiscount = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_PRICE_DISCOUNT)

                        val item = ItemPromoModel(
                            itemId,
                            itemName,
                            itemPrice.toInt(),
                            itemDimension.toInt(),
                            itemUnit,
                            itemBadge,
                            itemCategory,
                            itemOwnerId,
                            itemUpdatedAt,
                            itemCreatedAt,
                            itemDesc,
                            itemInfo,
                            itemStatus,
                            itemPriceDiscount.toInt()
                        )

                        view.showItem(item)
                        view.setTab(itemDesc, itemInfo)
                    }

                    REQUEST_ITEMS_RELATED -> {
                        val itemsJsonArray = response.getJSONArray(Constant.API_RESPONSE_BODY_ITEM)
                        val collectionType = object : TypeToken<Collection<ItemPromoModel>>() {}.type
                        val itemsGSon =
                            Gson().fromJson<Collection<ItemPromoModel>>(itemsJsonArray.toString(), collectionType)

                        view.progressBarHide(R.id.pb_items_related)
                        view.showItemsRelated(itemsGSon)
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

    override fun requestItem(id: String) {
        val thisFun = Throwable().stackTrace[0].methodName

        RequestAPI(
            context,
            Constant.URL_ITEM_DETAIL + id, {
                onResponse(it, thisFun, REQUEST_ITEM)
            }, {
                onError(it, thisFun, REQUEST_ITEM)
            }
        ).requestGet(
            arrayOf(
                API_REQUEST_PARAM_NAME_CONTENT_TYPE to API_REQUEST_PARAM_VALUE_AOX
            )
        )
    }

    override fun requestItemsRelated(activity: FragmentActivity, id: String) {
        val thisFun = Throwable().stackTrace[0].methodName
        fragmentActivity = activity

        RequestAPI(
            context,
            Constant.URL_ITEM_RELATED + id, {
                onResponse(it, thisFun, REQUEST_ITEMS_RELATED)
            }, {
                onError(it, thisFun, REQUEST_ITEMS_RELATED)
            }
        ).requestGet(
            arrayOf(
                API_REQUEST_PARAM_NAME_CONTENT_TYPE to API_REQUEST_PARAM_VALUE_AOX
            )
        )
    }

    companion object {
        private var fragmentActivity: FragmentActivity? = null
        private const val REQUEST_ITEM = 1
        private const val REQUEST_ITEMS_RELATED = 2
    }
}