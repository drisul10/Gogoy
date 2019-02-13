package com.gogoy.components.auth.recovery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.setContentView

class SendEmailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO: actionbar
        //set UI
        SendEmailUI().setContentView(this)
    }
}