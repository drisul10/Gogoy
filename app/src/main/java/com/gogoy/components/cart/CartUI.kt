package com.gogoy.components.cart

import android.widget.Toolbar
import com.gogoy.R
import org.jetbrains.anko.*

class CartUI : AnkoComponent<CartActivity> {

    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<CartActivity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            toolbar = toolbar {
                lparams(width = matchParent, height = wrapContent)
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