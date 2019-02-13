package com.gogoy.components

import android.view.Gravity
import com.gogoy.R
import org.jetbrains.anko.*

class SplashUI : AnkoComponent<SplashActivity> {

    override fun createView(ui: AnkoContext<SplashActivity>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER
            backgroundColorResource = R.color.colorPrimary

            imageView {
                imageResource = R.drawable.gogoy
            }.lparams(width = dip(200), height = matchParent)
        }
    }
}