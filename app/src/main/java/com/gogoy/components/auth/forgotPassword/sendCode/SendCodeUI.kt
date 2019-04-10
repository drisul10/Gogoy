package com.gogoy.components.auth.forgotPassword.sendCode

import com.gogoy.R
import org.jetbrains.anko.*

class SendCodeUI : AnkoComponent<SendCodeActivity> {

    override fun createView(ui: AnkoContext<SendCodeActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)

            frameLayout {
                lparams(width = matchParent, height = matchParent)
                id = R.id.fl_main_content
            }
        }
    }

    companion object {
        fun instance() = SendCodeUI()
    }
}