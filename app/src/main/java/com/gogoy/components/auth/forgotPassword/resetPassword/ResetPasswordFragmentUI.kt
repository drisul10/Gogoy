package com.gogoy.components.auth.forgotPassword.resetPassword

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import androidx.fragment.app.Fragment
import com.gogoy.R
import org.jetbrains.anko.*

class ResetPasswordFragmentUI<T> : AnkoComponent<T> {

    lateinit var btnProcess: Button

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            id = R.id.rl_root
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
                        id = R.id.et_password
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
                        id = R.id.et_password_confirm
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

                //button process
                btnProcess = button {
                    textResource = R.string.process
                    textColorResource = R.color.white
                    backgroundResource = R.drawable.border_color_primary
                }.lparams(width = matchParent, height = wrapContent)
            }
        }
    }

    companion object {
        fun instance() = ResetPasswordFragmentUI<Fragment>()
    }
}