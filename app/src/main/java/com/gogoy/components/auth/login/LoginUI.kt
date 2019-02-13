package com.gogoy.components.auth.login

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.widget.LinearLayout
import com.gogoy.R
import com.gogoy.components.auth.recovery.SendEmailActivity
import com.gogoy.components.auth.register.RegisterActivity
import com.gogoy.components.main.MainActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginUI : AnkoComponent<LoginActivity> {

    override fun createView(ui: AnkoContext<LoginActivity>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER
            backgroundColorResource = R.color.white

            //container
            verticalLayout {
                lparams(width = dip(300), height = wrapContent)

                imageView {
                    imageResource = R.drawable.icon_login
                }.lparams(width = dip(300), height = wrapContent)

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

                //field password
                //TODO: option visible password
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        bottomMargin = dip(20)
                    }

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent) {
                            bottomMargin = dip(10)
                            weightSum = 2f
                        }
                        orientation = LinearLayout.HORIZONTAL

                        textView {
                            gravity = Gravity.START
                            textResource = R.string.paswd
                            textColorResource = R.color.black
                            typeface = Typeface.DEFAULT_BOLD
                        }.lparams {
                            weight = 1f
                        }

                        textView {
                            gravity = Gravity.END
                            textResource = R.string.forgot_paswd
                            textColorResource = R.color.blueDeepSky
                            typeface = Typeface.DEFAULT_BOLD
                            isClickable = true

                            onClick {
                                startActivity<SendEmailActivity>()
                            }
                        }.lparams {
                            weight = 1f
                        }
                    }

                    editText {
                        padding = dip(15)
                        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        textSize = 16f
                        textColorResource = R.color.black
                        hintResource = R.string.fill_paswd
                        hintTextColor = R.color.colorText
                        backgroundResource = R.drawable.edit_text_states
                    }.lparams {
                        width = matchParent
                        height = wrapContent
                    }
                }

                //button submit
                button {
                    textResource = R.string.login
                    textColorResource = R.color.white
                    backgroundResource = R.drawable.border_color_primary

                    onClick {
                        //TODO: autentikasi dari API
                        startActivity<MainActivity>()
                    }
                }.lparams(width = matchParent, height = wrapContent)

                //text register clickable
                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        topMargin = dip(30)
                    }
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_HORIZONTAL

                    textView {
                        textResource = R.string.dont_have_account
                        textColorResource = R.color.colorText
                        //for spacing :v
                        rightPadding = dip(5)
                    }

                    textView {
                        textResource = R.string.register_now
                        textColorResource = R.color.blueDeepSky
                        typeface = Typeface.DEFAULT_BOLD
                        isClickable = true

                        onClick {
                            startActivity<RegisterActivity>()
                        }
                    }
                }
            }
        }
    }
}