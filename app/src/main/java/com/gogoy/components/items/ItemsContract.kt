package com.gogoy.components.items

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface ItemsContract {
    interface View : BaseView<Presenter> {
        fun showItem()
    }

    interface Presenter : BasePresenter {
        fun loadItem()
    }
}