package com.gogoy.components.auth.register

import android.graphics.Typeface
import android.text.InputType
import android.view.Gravity
import android.widget.Button
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class RegisterUI : AnkoComponent<RegisterActivity> {

    lateinit var btnRegister: Button

    override fun createView(ui: AnkoContext<RegisterActivity>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            nestedScrollView {
                lparams(width = matchParent, height = matchParent)

                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    gravity = Gravity.CENTER

                    //container
                    verticalLayout {
                        lparams(width = dip(300), height = wrapContent)

                        //TODO: imageView

                        //field full name
                        verticalLayout {
                            lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(20)
                            }

                            textView {
                                gravity = Gravity.START
                                textResource = R.string.full_name
                                textColorResource = R.color.black
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams {
                                bottomMargin = dip(10)
                            }

                            editText {
                                id = R.id.et_display_name
                                padding = dip(15)
                                textSize = 16f
                                textColorResource = R.color.black
                                hintResource = R.string.fill_full_name
                                hintTextColor = R.color.colorText
                                backgroundResource = R.drawable.edit_text_states
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }
                        }

                        //field mobile number
                        verticalLayout {
                            lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(20)
                            }

                            textView {
                                gravity = Gravity.START
                                textResource = R.string.mobile_number
                                textColorResource = R.color.black
                                typeface = Typeface.DEFAULT_BOLD
                            }.lparams {
                                bottomMargin = dip(10)
                            }

                            editText {
                                id = R.id.et_phone
                                padding = dip(15)
                                textSize = 16f
                                textColorResource = R.color.black
                                hintResource = R.string.fill_mobile_number
                                hintTextColor = R.color.colorText
                                backgroundResource = R.drawable.edit_text_states
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }
                        }

                        //field email
                        verticalLayout {
                            lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(20)
                            }

                            textView {
                                gravity = Gravity.START
                                textResource = R.string.email
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

                        //field password
                        verticalLayout {
                            lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(20)
                            }

                            textView {
                                gravity = Gravity.START
                                textResource = R.string.paswd
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
                                hintResource = R.string.fill_paswd
                                hintTextColor = R.color.colorText
                                backgroundResource = R.drawable.edit_text_states
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }
                        }

                        //field repeat password
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

                        //button submit
                        btnRegister = button {
                            textResource = R.string.register
                            textColorResource = R.color.white
                            backgroundResource = R.drawable.border_color_primary
                        }.lparams(width = matchParent, height = wrapContent)
                    }
                }
            }
        }
    }
}