package com.gogoy.components.bengkel

import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.slidingPaneLayout

class BengkelFragmentUI<T> : AnkoComponent<T> {
    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            slidingPaneLayout {
                lparams(width = matchParent, height = matchParent)

                textView {
                    text = "COBA"
                    textSize = 24f
                    textColorResource = R.color.colorText
                }
            }
        }
    }
}