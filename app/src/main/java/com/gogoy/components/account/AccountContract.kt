package com.gogoy.components.account

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface AccountContract {
    interface View : BaseView<Presenter> {
        fun showMenu()
    }

    interface Presenter : BasePresenter {
        fun loadMenu()
    }
}