package com.gogoy.components.auth.register

import androidx.fragment.app.FragmentActivity
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface RegisterContract {
    interface View : BaseView<Presenter> {
        fun clickRegister()
    }

    interface Presenter : BasePresenter {
        fun requestDataUser(token: String)
        fun requestLogin(activity: FragmentActivity?)
        fun requestRegister(v: android.view.View)
    }
}