package com.gogoy.components.main

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.R
import com.gogoy.data.models.BannerModel
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.sentry.Sentry
import io.sentry.event.UserBuilder
import org.json.JSONObject

class MainPresenter(private var view: MainContract.View) : MainContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        view.hideBanner()
        view.hidePromoKomoditi()
        view.hidePromoSayur()
        view.progressBarShow(R.id.pb_banner)
        view.progressBarShow(R.id.pb_promo_komoditi)
        view.progressBarShow(R.id.pb_promo_sayur)
        view.onClickViewAllPromoById()
        view.onPresenterStart()
        view.onSwipeRefresh()
        loadCategory()
        loadBestSeller()
    }

    override fun loadCategory() {
        view.showCategory()
    }

    override fun handleError(error: VolleyError, context: Context, func: String, id: Int) {
        val errorMessage = getErrorMessageVolley(error, context)
        Sentry.getContext().user = UserBuilder()
            .setData(setParamsSentry(errorMessage, this, context, func))
            .build()
        Sentry.capture(setMessageSentry(this, errorMessage))

        view.progressBarHide(id)
        view.snackbarShow(errorMessage, Color.RED)
        view.snackbarDismissOn(5000)
    }

    override fun handleResponse(response: JSONObject, context: Context, func: String, id: Int) {
        view.progressBarHide(id)
        when (response.getBoolean(Constant.API_RESPONSE_BODY_STATUS)) {
            true -> {
                val itemsJsonArray = response.getJSONArray(Constant.API_RESPONSE_BODY_ITEM)
                val gSon = Gson()

                when (id) {
                    R.id.pb_banner -> {
                        val collectionType = object : TypeToken<Collection<BannerModel>>() {}.type
                        val itemsGSon =
                            gSon.fromJson<Collection<BannerModel>>(itemsJsonArray.toString(), collectionType)
                        view.showBanner(itemsGSon)
                    }
                    R.id.pb_promo_komoditi -> {
                        val collectionType = object : TypeToken<Collection<ItemPromoModel>>() {}.type
                        val itemsGSon =
                            gSon.fromJson<Collection<ItemPromoModel>>(itemsJsonArray.toString(), collectionType)
                        view.showPromoKomoditi(itemsGSon)
                    }
                    R.id.pb_promo_sayur -> {
                        val collectionType = object : TypeToken<Collection<ItemPromoModel>>() {}.type
                        val itemsGSon =
                            gSon.fromJson<Collection<ItemPromoModel>>(itemsJsonArray.toString(), collectionType)
                        view.showPromoSayur(itemsGSon)
                    }
                }
            }
        }
    }

    override fun loadBanner(context: Context, activity: FragmentActivity) {
        val thisFun = Throwable().stackTrace[0].methodName

        RequestAPI(
            context,
            Constant.URL_BANNER, {
                this.handleResponse(it, context, thisFun, R.id.pb_banner)
            }, {
                this.handleError(it, context, thisFun, R.id.pb_banner)
            }
        ).requestGet(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX
            )
        )
    }

    override fun loadPromoKomoditi(context: Context, activity: FragmentActivity) {
        val thisFun = Throwable().stackTrace[0].methodName

        RequestAPI(
            context,
            Constant.URL_ITEM_PROMO_KOMODITI, {
                this.handleResponse(it, context, thisFun, R.id.pb_promo_komoditi)
            }, {
                this.handleError(it, context, thisFun, R.id.pb_promo_komoditi)
            }
        ).requestGet(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX
            )
        )
    }

    override fun loadPromoSayur(context: Context, activity: FragmentActivity) {
        val thisFun = Throwable().stackTrace[0].methodName

        RequestAPI(
            context,
            Constant.URL_ITEM_PROMO_SAYUR, {
                this.handleResponse(it, context, thisFun, R.id.pb_promo_sayur)
            }, {
                this.handleError(it, context, thisFun, R.id.pb_promo_sayur)
            }
        ).requestGet(
            arrayOf(
                Constant.API_REQUEST_PARAM_NAME_CONTENT_TYPE to Constant.API_REQUEST_PARAM_VALUE_AOX
            )
        )
    }

    override fun loadBestSeller() {
        view.showBestSeller()
    }
}