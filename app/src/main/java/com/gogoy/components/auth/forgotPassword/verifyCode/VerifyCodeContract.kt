package com.gogoy.components.auth.forgotPassword.verifyCode

import androidx.fragment.app.FragmentActivity
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView

interface VerifyCodeContract {
    interface View : BaseView<Presenter> {
        fun hideBtnResend()
        fun hideCountDown()
        fun onClickResend()
        fun onClickValidate()
        fun onTypeOtpComplete()
        fun showBtnResend()
        fun showCountDown()
        fun showHintGetOtp()
    }

    interface Presenter : BasePresenter {
        fun requestValidateCode(activity: FragmentActivity, strOtpCode: String, strEmail: String)
        fun requestResendCode(strEmail: String)
    }
}