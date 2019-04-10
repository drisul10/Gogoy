package com.gogoy.components.main

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.android.volley.VolleyError
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView
import com.gogoy.data.models.BannerModel
import com.gogoy.data.models.ItemPromoModel
import org.json.JSONObject

interface MainContract {

    interface View : BaseView<Presenter> {
        fun hideBanner()
        fun hidePromoKomoditi()
        fun hidePromoSayur()
        fun onClickViewAllPromoById()
        fun onSwipeRefresh()
        fun showBanner(data: Collection<BannerModel>)
        fun showBestSeller()
        fun showCategory()
        fun showPromoKomoditi(data: Collection<ItemPromoModel>)
        fun showPromoSayur(data: Collection<ItemPromoModel>)
    }

    interface Presenter : BasePresenter {
        fun handleError(error: VolleyError, context: Context, func: String, id: Int)
        fun handleResponse(response: JSONObject, context: Context, func: String, id: Int)
        fun loadBanner(context: Context, activity: FragmentActivity)
        fun loadCategory()
        fun loadPromoKomoditi(context: Context, activity: FragmentActivity)
        fun loadPromoSayur(context: Context, activity: FragmentActivity)
        fun loadBestSeller()
    }
}