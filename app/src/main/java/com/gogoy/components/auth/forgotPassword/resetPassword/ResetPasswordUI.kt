package com.gogoy.components.auth.forgotPassword.resetPassword

import com.gogoy.R
import org.jetbrains.anko.*

class ResetPasswordUI : AnkoComponent<ResetPasswordActivity> {

    override fun createView(ui: AnkoContext<ResetPasswordActivity>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)

            frameLayout {
                lparams(width = matchParent, height = matchParent)
                id = R.id.fl_main_content
            }
        }
    }

    companion object {
        fun instance() = ResetPasswordUI()
    }
}