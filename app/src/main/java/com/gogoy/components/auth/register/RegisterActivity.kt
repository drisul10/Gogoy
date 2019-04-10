package com.gogoy.components.auth.register

import android.os.Bundle
import com.gogoy.R
import com.gogoy.components.base.BaseActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class RegisterActivity : BaseActivity() {

    private lateinit var registerPresenter: RegisterPresenter
    val ui = RegisterUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        val registerFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as RegisterFragment? ?: RegisterFragment.instance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }

        registerPresenter = RegisterPresenter(registerFragment, applicationContext)
    }

    override fun onNoInternet() {
        //TODO
    }
}