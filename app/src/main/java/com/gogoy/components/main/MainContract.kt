package com.gogoy.components.main

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface MainContract {

    interface View : BaseView<Presenter> {
        fun showBanner()
        fun showCategory()
        fun showPromo()
        fun showBestSeller()
    }

    interface Presenter : BasePresenter {
        fun loadBanner()
        fun loadCategory()
        fun loadPromo()
        fun loadBestSeller()
    }
}