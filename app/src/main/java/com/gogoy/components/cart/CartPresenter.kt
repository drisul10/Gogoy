package com.gogoy.components.cart

import android.content.Context
import android.graphics.Color
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.*
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.json.JSONException
import org.json.JSONObject

class CartPresenter(val view: CartContract.View, val context: Context) : CartContract.Presenter, HandleRequestAPI {

    private var i = 1
    private var items: MutableList<ItemPromoModel> = mutableListOf()
    private val sharedPref = SharedPref(context)

    init {
        view.presenter = this
    }

    override fun start() {
        view.onPresenterStart()
    }

    override fun loadItem() {
    }

    override fun onError(error: VolleyError, func: String, id: Any) {
        val errorMessage = getErrorMessageVolley(error, context)
        Sentry.getContext().user = UserBuilder()
            .setData(setParamsSentry(errorMessage, this, context, func))
            .build()
        Sentry.capture(setMessageSentry(this, errorMessage))

        view.progressBarHide(REQUEST_ITEM)
        view.snackbarShow(errorMessage, Color.RED)
        view.snackbarDismissOn(5000)
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
                        val itemDesc = "itemJSONObject.getString(Constant.API_RESPONSE_FIELD_DESC)"
                        val itemInfo = "itemJSONObject.getString(Constant.API_RESPONSE_FIELD_INFO)"
                        val itemStock = "itemJSONObject.getString(Constant.API_RESPONSE_FIELD_STOCK)"
                        val itemBadge = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_BADGE)
                        val itemCategory = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_CATEGORY)
                        val itemOwnerId = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_OWNER_ID)
                        val itemStatus = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_STATUS)
                        val itemUpdatedAt = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_UPDATED_AT)
                        val itemCreatedAt = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_CREATED_AT)
                        val itemPriceDiscount =
                            if (itemJSONObject.has(Constant.API_RESPONSE_FIELD_PRICE_DISCOUNT))
                                itemJSONObject.getString(Constant.API_RESPONSE_FIELD_PRICE_DISCOUNT).toInt()
                            else 0

                        items.add(
                            ItemPromoModel(
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
                                itemPriceDiscount
                            )
                        )

                        view.showItem(items)

                        if (i >= sharedPref.cartGetItems().size) {
                            view.progressBarHide(R.id.pb_items_cart)
                            view.showUI(R.id.rv_cart_item)
                        }

                        i++
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

    override fun requestItem(isSwipe: Boolean) {
        val thisFun = Throwable().stackTrace[0].methodName
        val itemsCart = sharedPref.cartGetItems()

        if (isSwipe) {
            items.clear()
            i = 1
        }

        itemsCart.forEach {
            RequestAPI(
                context,
                Constant.URL_ITEM_DETAIL + it.first, { response ->
                    onResponse(response, thisFun, REQUEST_ITEM)
                }, { error ->
                    onError(error, thisFun, REQUEST_ITEM)
                }
            ).requestGet(
                arrayOf(
                    Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX
                )
            )
        }
    }

    companion object {
        const val REQUEST_ITEM = 1
    }
}