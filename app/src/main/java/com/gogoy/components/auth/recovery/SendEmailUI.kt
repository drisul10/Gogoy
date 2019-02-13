package com.gogoy.components.auth.recovery

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SendEmailUI : AnkoComponent<SendEmailActivity> {

    override fun createView(ui: AnkoContext<SendEmailActivity>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER
            backgroundColorResource = R.color.white

            //container
            verticalLayout {
                lparams(width = dip(300), height = wrapContent)

                //field email
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        bottomMargin = dip(20)
                    }

                    textView {
                        gravity = Gravity.START
                        textResource = R.string.email_addr
                        textColorResource = R.color.black
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        bottomMargin = dip(10)
                    }

                    editText {
                        padding = dip(15)
                        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        textSize = 16f
                        textColorResource = R.color.black
                        hintResource = R.string.fill_email
                        hintTextColor = R.color.colorText
                        backgroundResource = R.drawable.edit_text_states
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }

                //button submit
                button {
                    textResource = R.string.send
                    textColorResource = R.color.white
                    backgroundResource = R.drawable.border_color_primary

                    onClick {
                        //TODO: send email and access link to reset password
                        //option new activity for information
                        startActivity<ResetPasswordActivity>()
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }
}