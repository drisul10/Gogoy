package com.gogoy.components.bengkel

import android.graphics.Color
import android.widget.Toolbar
import com.gogoy.R
import org.jetbrains.anko.*

class BengkelUI : AnkoComponent<BengkelActivity> {

    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<BengkelActivity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            toolbar = toolbar {
                lparams(width = matchParent, height = wrapContent)
                setTitleTextColor(Color.WHITE)
                setSubtitleTextColor(Color.WHITE)
                backgroundColorResource = R.color.colorPrimary
            }

            //frame for fragment
            frameLayout {
                lparams(width = matchParent, height = wrapContent)
                id = R.id.fl_main_content
            }
        }
    }
}