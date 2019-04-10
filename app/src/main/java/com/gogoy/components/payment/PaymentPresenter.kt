package com.gogoy.components.payment

import android.content.Context
import android.graphics.Color
import com.android.volley.VolleyError
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.*
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.json.JSONException
import org.json.JSONObject

class PaymentPresenter(val view: PaymentContract.View, val context: Context) : PaymentContract.Presenter,
    HandleRequestAPI {

    var items: MutableList<ItemPromoModel> = mutableListOf()

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
                        val itemPrice = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_PRICE).toInt()
                        val itemDimension = 0
                        val itemUnit = Constant.NULL
                        val itemBadge = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_BADGE)
                        val itemCategory = Constant.NULL
                        val itemOwnerId = itemJSONObject.getString(Constant.API_RESPONSE_FIELD_OWNER_ID)
                        val itemPriceDiscount =
                            if (itemJSONObject.has(Constant.API_RESPONSE_FIELD_PRICE_DISCOUNT))
                                itemJSONObject.getString(Constant.API_RESPONSE_FIELD_PRICE_DISCOUNT).toInt()
                            else 0

                        items.add(
                            ItemPromoModel(
                                itemId,
                                itemName,
                                itemPrice,
                                itemDimension,
                                itemUnit,
                                itemBadge,
                                itemCategory,
                                itemOwnerId,
                                Constant.NULL,
                                Constant.NULL,
                                Constant.NULL,
                                Constant.NULL,
                                Constant.NULL,
                                itemPriceDiscount
                            )
                        )
                        view.showItem(items)
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

    override fun requestItem(items: ArrayList<String>?, isSwipe: Boolean) {
        val thisFun = Throwable().stackTrace[0].methodName

        items?.forEach {
            RequestAPI(
                context,
                Constant.URL_ITEM_DETAIL + it, { response ->
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