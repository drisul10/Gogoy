package com.gogoy.components.auth.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        LoginUI().setContentView(this)
    }
}