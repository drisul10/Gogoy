package com.gogoy.components.auth.login

import com.gogoy.R
import org.jetbrains.anko.*

class LoginUI : AnkoComponent<LoginActivity> {

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)

            frameLayout {
                lparams(width = matchParent, height = matchParent)
                id = R.id.fl_main_content
            }
        }
    }

    companion object {
        fun instance() = LoginUI()
    }
}