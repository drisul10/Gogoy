package com.gogoy.components.main

import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class MainUI : AnkoComponent<MainActivity> {

    override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            swipeRefreshLayout {
                id = R.id.swipe_refresh

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

    companion object {
        fun instance() = MainUI()
    }
}