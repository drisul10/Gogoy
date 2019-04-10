package com.gogoy.components.item

import androidx.fragment.app.FragmentActivity
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView
import com.gogoy.data.models.ItemPromoModel

interface ItemContract {
    interface View : BaseView<Presenter> {
        fun clickButtonBuy()
        fun clickButtonMin()
        fun clickButtonPlus()
        fun setViewState()
        fun setTab(strDesc: String, strInfo: String)
        fun showItem(data: ItemPromoModel)
        fun showItemsRelated(data: Collection<ItemPromoModel>)
    }

    interface Presenter : BasePresenter {
        fun requestItem(id: String)
        fun requestItemsRelated(activity: FragmentActivity, id: String)
    }
}