package com.gogoy.components.splash

import android.view.Gravity
import androidx.fragment.app.Fragment
import com.gogoy.R
import org.jetbrains.anko.*

class SplashFragmentUI<T> : AnkoComponent<T> {

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER
            backgroundColorResource = R.color.colorPrimary

            imageView {
                imageResource = R.drawable.gondes
            }.lparams(width = dip(200), height = wrapContent)
        }
    }

    companion object {
        fun instance() = SplashFragmentUI<Fragment>()
    }
}