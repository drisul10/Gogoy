package com.gogoy.components.account.settings

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface SettingsContract {
    interface View : BaseView<Presenter> {
        fun showItem()
    }

    interface Presenter : BasePresenter {
        fun loadItem()
    }
}