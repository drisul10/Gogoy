package com.gogoy.components.auth.register

import com.gogoy.R
import org.jetbrains.anko.*

class RegisterUI : AnkoComponent<RegisterActivity> {

    override fun createView(ui: AnkoContext<RegisterActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)

            frameLayout {
                lparams(width = matchParent, height = matchParent)
                id = R.id.fl_main_content
            }
        }
    }

    companion object {
        fun instance() = RegisterUI()
    }
}