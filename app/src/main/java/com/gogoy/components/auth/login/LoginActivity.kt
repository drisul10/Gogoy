package com.gogoy.components.auth.login

import android.os.Bundle
import com.gogoy.R
import com.gogoy.components.base.BaseActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class LoginActivity : BaseActivity() {

    private lateinit var loginPresenter: LoginPresenter
    val ui = LoginUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        val loginFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as LoginFragment? ?: LoginFragment.instance()
            .also {
                replaceFragmentInActivity(it, R.id.fl_main_content)
            }

        loginPresenter = LoginPresenter(loginFragment, applicationContext)
    }

    override fun onNoInternet() {
        //TODO
    }
}