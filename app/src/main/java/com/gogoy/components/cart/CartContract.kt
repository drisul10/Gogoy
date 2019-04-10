package com.gogoy.components.cart

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView
import com.gogoy.data.models.ItemPromoModel

interface CartContract {
    interface View : BaseView<Presenter> {
        fun hideUI(id: Any)
        fun setViewState()
        fun showItem(data: MutableList<ItemPromoModel>)
        fun showUI(id: Any)
    }

    interface Presenter : BasePresenter {
        fun loadItem()
        fun requestItem(isSwipe: Boolean = false)
    }
}