package com.gogoy.components.account

import android.widget.Toolbar
import com.gogoy.R
import org.jetbrains.anko.*

class AccountUI : AnkoComponent<AccountActivity> {

    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<AccountActivity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            toolbar = toolbar {
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