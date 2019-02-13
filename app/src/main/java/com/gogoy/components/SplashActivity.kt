package com.gogoy.components

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.components.auth.login.LoginActivity
import com.gogoy.components.main.MainActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SplashUI().setContentView(this)

        //TODO: splash screen only when first open, pass when connect internet
        Handler().postDelayed({
            //TODO: condition IF is logic (when already login, pass)
            if (1 > 2) {
                startActivity<MainActivity>()
            } else {
                startActivity<LoginActivity>()
            }
            finish()
        }, 2000)
    }
}