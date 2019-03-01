package com.gogoy.components.bengkel

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface BengkelContract {
    interface View : BaseView<Presenter> {
        fun showMaps()
        fun showSearch()
    }

    interface Presenter : BasePresenter {
        fun loadMaps()
        fun loadSearch()
    }
}