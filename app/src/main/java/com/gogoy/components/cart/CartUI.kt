package com.gogoy.components.cart

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar

class CartUI : AnkoComponent<CartActivity> {

    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<CartActivity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            toolbar = themedToolbar {
                lparams(width = matchParent, height = wrapContent)
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