package com.gogoy.components.auth.forgotPassword.verifyCode

import android.os.Bundle
import com.gogoy.R
import com.gogoy.components.base.BaseActivity
import com.gogoy.utils.Constant
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class VerifyCodeActivity : BaseActivity() {

    private val ui = VerifyCodeUI.instance()
    private lateinit var verifyCodePresenter: VerifyCodePresenter
    private lateinit var intentEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)

        intentEmail = intent.getStringExtra(Constant.UP_EMAIL)
        val bundle = Bundle()
        bundle.putString(Constant.UP_EMAIL, intentEmail)

        val fragmentObj = VerifyCodeFragment.instance()
        fragmentObj.arguments = bundle

        val verifyCodeFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as VerifyCodeFragment? ?: fragmentObj.also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }

        verifyCodePresenter = VerifyCodePresenter(verifyCodeFragment, applicationContext)
    }

    override fun onNoInternet() {

    }
}