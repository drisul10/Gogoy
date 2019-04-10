package com.gogoy.components.auth.login

import androidx.fragment.app.FragmentActivity
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface LoginContract {
    interface View : BaseView<Presenter> {
        fun clickForgotPassword()
        fun clickLogin()
        fun clickRegister()
    }

    interface Presenter : BasePresenter {
        fun requestDataUser(token: String)
        fun requestLogin(v: android.view.View?, activity: FragmentActivity)
    }
}