package com.gogoy.components.splash

import android.os.Bundle
import com.gogoy.R
import com.gogoy.components.base.BaseActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class SplashActivity : BaseActivity() {

    private lateinit var splashPresenter: SplashPresenter
    val ui = SplashUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        val splashFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as SplashFragment? ?: SplashFragment.instance()
            .also {
                replaceFragmentInActivity(it, R.id.fl_main_content)
            }

        splashPresenter = SplashPresenter(applicationContext, splashFragment)
    }

    override fun onNoInternet() {
        val fragment: SplashFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as SplashFragment
        fragment.handleRoute()
    }
}