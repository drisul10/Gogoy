package com.gogoy.components.cart

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface CartContract {
    interface View : BaseView<Presenter> {
        fun showItem()
    }

    interface Presenter : BasePresenter {
        fun loadItem()
    }
}