package com.gogoy.components.auth.forgotPassword.verifyCode

import com.gogoy.R
import org.jetbrains.anko.*

class VerifyCodeUI : AnkoComponent<VerifyCodeActivity> {

    override fun createView(ui: AnkoContext<VerifyCodeActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)

            frameLayout {
                lparams(width = matchParent, height = matchParent)
                id = R.id.fl_main_content
            }
        }
    }

    companion object {
        fun instance() = VerifyCodeUI()
    }
}