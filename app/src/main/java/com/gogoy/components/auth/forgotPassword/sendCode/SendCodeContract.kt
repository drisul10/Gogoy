package com.gogoy.components.auth.forgotPassword.sendCode

import androidx.fragment.app.FragmentActivity
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface SendCodeContract {
    interface View : BaseView<Presenter> {
        fun onClickReset()
    }

    interface Presenter : BasePresenter {
        fun sendCodeToEmail(v: android.view.View, activity: FragmentActivity)
    }
}