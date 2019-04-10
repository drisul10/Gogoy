package com.gogoy.components.auth.forgotPassword.sendCode

import android.os.Bundle
import com.gogoy.R
import com.gogoy.components.base.BaseActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class SendCodeActivity : BaseActivity() {

    private lateinit var sendCodePresenter: SendCodePresenter
    val ui = SendCodeUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        val loginFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as SendCodeFragment? ?: SendCodeFragment.instance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        sendCodePresenter = SendCodePresenter(loginFragment, applicationContext)
    }

    override fun onNoInternet() {
        //TODO
    }
}