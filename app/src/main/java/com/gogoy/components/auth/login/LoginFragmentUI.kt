package com.gogoy.components.auth.login

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gogoy.R
import org.jetbrains.anko.*

class LoginFragmentUI<T> : AnkoComponent<T> {

    lateinit var btnLogin: Button
    lateinit var tvRegister: TextView
    lateinit var tvForgotPassword: TextView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(height = matchParent, width = matchParent)
            backgroundColorResource = R.color.white
            gravity = Gravity.CENTER
            id = R.id.rl_root

            //container
            verticalLayout {
                lparams(height = wrapContent, width = dip(300))

                //logo login
                imageView {
                    imageResource = R.drawable.round
                }.lparams(height = dip(150), width = dip(150)) {
                    bottomMargin = dip(25)
                    gravity = Gravity.CENTER_HORIZONTAL
                }

                //wrapper email
                verticalLayout {
                    lparams(height = wrapContent, width = matchParent) {
                        bottomMargin = dip(20)
                    }

                    textView {
                        gravity = Gravity.START
                        textColorResource = R.color.black
                        textResource = R.string.email_addr
                        typeface = Typeface.DEFAULT_BOLD
                    }.lparams {
                        bottomMargin = dip(10)
                    }

                    editText {
                        backgroundResource = R.drawable.edit_text_states
                        hintResource = R.string.fill_email
                        hintTextColor = R.color.colorText
                        id = R.id.et_email
                        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        padding = dip(15)
                        textColorResource = R.color.black
                        textSize = 16f
                    }.lparams(height = matchParent, width = matchParent)
                }

                //wrapper password
                //TODO: option visible password
                verticalLayout {
                    lparams(height = wrapContent, width = matchParent) {
                        bottomMargin = dip(20)
                    }

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent) {
                            bottomMargin = dip(10)
                            orientation = LinearLayout.HORIZONTAL
                            weightSum = 2f
                        }

                        textView {
                            gravity = Gravity.START
                            textColorResource = R.color.black
                            textResource = R.string.paswd
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams {
                            weight = 1f
                        }

                        tvForgotPassword = textView {
                            gravity = Gravity.END
                            isClickable = true
                            textColorResource = R.color.blueDeepSky
                            textResource = R.string.forgot_paswd
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams {
                            weight = 1f
                        }
                    }

                    editText {
                        backgroundResource = R.drawable.edit_text_states
                        hintTextColor = R.color.colorText
                        hintResource = R.string.fill_paswd
                        id = R.id.et_password
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        padding = dip(15)
                        textColorResource = R.color.black
                        textSize = 16f
                    }.lparams(height = matchParent, width = matchParent)
                }

                //button login
                btnLogin = button {
                    backgroundResource = R.drawable.border_color_primary
                    elevation = 5f
                    textColorResource = R.color.white
                    textResource = R.string.login
                }.lparams(width = matchParent, height = wrapContent)

                //wrapper clickable-text register
                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        orientation = LinearLayout.HORIZONTAL
                        topMargin = dip(30)
                    }

                    textView {
                        rightPadding = dip(5)
                        textColorResource = R.color.colorText
                        textResource = R.string.dont_have_account
                    }

                    tvRegister = textView {
                        isClickable = true
                        textColorResource = R.color.blueDeepSky
                        textResource = R.string.register_now
                        typeface = Typeface.DEFAULT_BOLD
                    }
                }
            }
        }
    }

    companion object {
        fun instance() = LoginFragmentUI<Fragment>()
    }
}