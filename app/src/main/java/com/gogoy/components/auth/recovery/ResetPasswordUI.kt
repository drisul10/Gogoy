package com.gogoy.components.auth.recovery

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import com.gogoy.R
import com.gogoy.components.auth.login.LoginActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ResetPasswordUI : AnkoComponent<ResetPasswordActivity> {

    override fun createView(ui: AnkoContext<ResetPasswordActivity>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER
            backgroundColorResource = R.color.white

            //container
            verticalLayout {
                lparams(width = dip(300), height = wrapContent)

                //field password
                //TODO: option visible password
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        bottomMargin = dip(20)
                    }

                    textView {
                        gravity = Gravity.START
                        textResource = R.string.paswd_new
                        textColorResource = R.color.black
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        bottomMargin = dip(10)
                    }

                    editText {
                        padding = dip(15)
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        textSize = 16f
                        textColorResource = R.color.black
                        hintResource = R.string.fill_paswd_new
                        hintTextColor = R.color.colorText
                        backgroundResource = R.drawable.edit_text_states
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }

                //field repeat password
                //TODO: option visible password
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        bottomMargin = dip(20)
                    }

                    textView {
                        gravity = Gravity.START
                        textResource = R.string.paswd_repeat
                        textColorResource = R.color.black
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        bottomMargin = dip(10)
                    }

                    editText {
                        padding = dip(15)
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        textSize = 16f
                        textColorResource = R.color.black
                        hintResource = R.string.fill_paswd_repeat
                        hintTextColor = R.color.colorText
                        backgroundResource = R.drawable.edit_text_states
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }

                //button submit
                button {
                    textResource = R.string.done
                    textColorResource = R.color.white
                    backgroundResource = R.drawable.border_color_primary

                    onClick {
                        //TODO: option new activity to show info
                        startActivity<LoginActivity>()
                    }
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }
}