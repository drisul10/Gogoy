package com.gogoy.components.item

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface ItemContract {
    interface View : BaseView<Presenter> {
        fun showItemRelated()
    }

    interface Presenter : BasePresenter {
        fun loadItemRelated()
    }
}