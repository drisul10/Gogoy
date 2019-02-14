package com.gogoy.components.main

import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class MainUI : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            //scroll view
            nestedScrollView {
                lparams(width = matchParent, height = matchParent)

                //frame for fragment
                frameLayout {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.fl_main_content
                }
            }
        }
    }
}