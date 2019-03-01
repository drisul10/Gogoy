package com.gogoy.components.item

import android.graphics.Color
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar

class ItemUI : AnkoComponent<ItemActivity> {

    lateinit var toolbar: androidx.appcompat.widget.Toolbar

    override fun createView(ui: AnkoContext<ItemActivity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            toolbar = themedToolbar {
                elevation = 5f
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