package com.gogoy.components.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gogoy.R
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Appbar
        super.onCreate(savedInstanceState)

        //set UI
        MainUI().setContentView(this)

        //set default fragment
        val mainFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as MainFragment? ?: MainFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        mainPresenter = MainPresenter(mainFragment)
    }
}