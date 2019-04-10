package com.gogoy.components.auth.forgotPassword.verifyCode

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.utils.*
import com.google.android.material.snackbar.Snackbar
import com.mukesh.OtpView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.find
import java.util.concurrent.TimeUnit

class VerifyCodeFragment : Fragment(), VerifyCodeContract.View, HandleMessage {

    private var argEmail: String? = null
    private lateinit var btnResend: Button
    private lateinit var btnValidate: Button
    private lateinit var ovCode: OtpView
    override lateinit var presenter: VerifyCodeContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    private var strCode: String? = null
    private lateinit var tvCountDown: TextView
    private lateinit var tvMessage: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        argEmail = arguments!!.getString(Constant.UP_EMAIL)
        return inflater.inflate(R.layout.layout_fragment_verify_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnResend = find(R.id.btn_resend)
        btnValidate = find(R.id.btn_validate)
        ovCode = find(R.id.ov_code)
        tvCountDown = find(R.id.tv_count_down)
        tvMessage = find(R.id.tv_message)

        presenter.start()
    }

    override fun onPresenterStart() {
        this.hideBtnResend()
        this.onClickResend()
        this.onClickValidate()
        this.onTypeOtpComplete()
        this.showCountDown()
        this.showHintGetOtp()
        this.snackbarInit()
    }

    override fun progressBarHide(id: Any) {
        //TODO
    }

    override fun hideBtnResend() {
        btnResend.gone()
    }

    override fun hideCountDown() {
        tvCountDown.gone()
    }

    override fun onClickResend() {
        btnResend.onClick {
            snackbarShow(getString(R.string.loading), Color.BLACK)
            presenter.requestResendCode(argEmail as String)
        }
    }

    override fun onClickValidate() {
        btnValidate.onClick {
            hideKeyboard()
            snackbarShow(getString(R.string.loading), Color.BLACK)

            if (strCode.isNullOrEmpty()) {
                snackbarChange(getString(R.string.fill_code), Color.RED)
                snackbarDismissOn(5000)
            } else {
                presenter.requestValidateCode(requireActivity(), strCode as String, argEmail as String)
            }
        }
    }

    override fun onTypeOtpComplete() {
        ovCode.setOtpCompletionListener { otp ->
            hideKeyboard()
            strCode = otp
            snackbarShow(getString(R.string.loading), Color.BLACK)
            presenter.requestValidateCode(requireActivity(), strCode as String, argEmail as String)
        }
    }

    override fun showBtnResend() {
        btnResend.visible()
    }

    override fun showCountDown() {
        tvCountDown.visible()

        object : CountDownTimer(120000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tvCountDown.text = String.format(
                    "%d menit, %d detik",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(
                            millisUntilFinished
                        )
                    )
                )
            }

            override fun onFinish() {
                tvCountDown.gone()
                btnResend.visible()
            }
        }.start()
    }

    override fun showHintGetOtp() {
        tvMessage.text = activity!!.applicationContext.getString(
            R.string.verify_code_message,
            argEmail!!.replace(
                "(^[^@]{3}|(?!^)\\G)[^@]".toRegex(),
                "$1*"
            )
        )
    }

    override fun progressBarShow(id: Any) {
        //TODO
    }

    override fun snackbarChange(message: String, color: Int) {
        super.change(message, color)
    }

    override fun snackbarDismissOn(delay: Long) {
        super.dismissOn(delay)
    }

    override fun snackbarInit() {
        rootLayout = find(R.id.rl_root)
        snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
    }

    override fun snackbarShow(message: String, color: Int) {
        super.show(message, color)
    }

    override fun swipe() {
        //TODO
    }

    companion object {
        fun instance() = VerifyCodeFragment()
    }
}