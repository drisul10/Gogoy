package com.gogoy.components.auth.forgotPassword.resetPassword

import androidx.fragment.app.FragmentActivity
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface ResetPasswordContract {
    interface View : BaseView<Presenter> {
        fun clickResetPassword()
    }

    interface Presenter : BasePresenter {
        fun resetResetPassword(
            v: android.view.View,
            activity: FragmentActivity,
            token: String
        )
    }
}