package com.gogoy.components.auth.forgotPassword.resetPassword

import android.os.Bundle
import com.gogoy.R
import com.gogoy.components.base.BaseActivity
import com.gogoy.utils.Constant
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class ResetPasswordActivity : BaseActivity() {

    private val ui = ResetPasswordUI.instance()
    private lateinit var resetPasswordPresenter: ResetPasswordPresenter
    private lateinit var intentToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)
        intentToken = intent.getStringExtra(Constant.UP_TOKEN)

        val bundle = Bundle()
        bundle.putString(Constant.UP_TOKEN, intentToken)

        val fragmentObj = ResetPasswordFragment.instance()
        fragmentObj.arguments = bundle

        val resetPasswordFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as ResetPasswordFragment? ?: fragmentObj.also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }

        resetPasswordPresenter = ResetPasswordPresenter(resetPasswordFragment, applicationContext)
    }

    override fun onNoInternet() {

    }
}