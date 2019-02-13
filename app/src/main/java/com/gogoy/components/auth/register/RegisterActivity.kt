package com.gogoy.components.auth.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RegisterUI().setContentView(this)
    }
}