package com.gogoy.components.payment

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView
import com.gogoy.data.models.ItemPromoModel

interface PaymentContract {
    interface View : BaseView<Presenter> {
        fun showItem(data: MutableList<ItemPromoModel>)
    }

    interface Presenter : BasePresenter {
        fun requestItem(items: ArrayList<String>?, isSwipe: Boolean = false)
    }
}