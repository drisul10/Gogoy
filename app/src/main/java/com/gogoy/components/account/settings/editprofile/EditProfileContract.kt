package com.gogoy.components.account.settings.editprofile

import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface EditProfileContract {
    interface View : BaseView<Presenter> {
        fun showForm()
    }

    interface Presenter : BasePresenter {
        fun loadForm()
    }
}