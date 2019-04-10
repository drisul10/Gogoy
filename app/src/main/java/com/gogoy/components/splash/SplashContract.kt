package com.gogoy.components.splash

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface SplashContract {

    interface View : BaseView<Presenter> {
        fun handleRoute()
    }

    interface Presenter : BasePresenter {
        fun requestDataUser(token: String)
    }
}