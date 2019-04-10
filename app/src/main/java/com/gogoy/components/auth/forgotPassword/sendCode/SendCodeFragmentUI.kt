package com.gogoy.components.auth.forgotPassword.sendCode

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import androidx.fragment.app.Fragment
import com.gogoy.R
import org.jetbrains.anko.*

class SendCodeFragmentUI<T> : AnkoComponent<T> {

    lateinit var btnReset: Button

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            id = R.id.rl_root
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
                        id = R.id.et_email
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

                //button reset password
                btnReset = button {
                    textResource = R.string.reset_password
                    textColorResource = R.color.white
                    backgroundResource = R.drawable.border_color_primary
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }

    companion object {
        fun instance() = SendCodeFragmentUI<Fragment>()
    }
}