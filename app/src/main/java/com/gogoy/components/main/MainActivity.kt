package com.gogoy.components.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        MainUI().setContentView(this)
    }
}