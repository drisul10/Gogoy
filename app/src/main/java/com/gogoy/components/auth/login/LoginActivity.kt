package com.gogoy.components.auth.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.setContentView

class LoginActivity : AppCompatActivity() {

    val ui = LoginUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        ui.btnLogin.onClick {
            startActivity(intentFor<MainActivity>().newTask().clearTask())
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }
}