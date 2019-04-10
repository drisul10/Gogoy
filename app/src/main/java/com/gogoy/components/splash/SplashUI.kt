package com.gogoy.components.splash

import com.gogoy.R
import org.jetbrains.anko.*

class SplashUI : AnkoComponent<SplashActivity> {

    override fun createView(ui: AnkoContext<SplashActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)

            frameLayout {
                lparams(width = matchParent, height = matchParent)
                id = R.id.fl_main_content
            }
        }
    }

    companion object {
        fun instance() = SplashUI()
    }
}