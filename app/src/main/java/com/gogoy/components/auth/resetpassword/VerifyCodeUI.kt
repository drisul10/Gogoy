package com.gogoy.components.auth.resetpassword

import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import com.gogoy.R
import org.jetbrains.anko.*

class VerifyCodeUI : AnkoComponent<VerifyCodeActivity> {

    lateinit var etCode: EditText
    lateinit var btnProcess: Button

    override fun createView(ui: AnkoContext<VerifyCodeActivity>) = with(ui) {
        linearLayout {
            id = R.id.ll_root
            lparams(width = matchParent, height = matchParent)
            gravity = Gravity.CENTER
            backgroundColorResource = R.color.white

            //container
            verticalLayout {
                lparams(width = dip(300), height = wrapContent)

                verticalLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        bottomMargin = dip(20)
                    }

                    etCode = editText {
                        padding = dip(15)
                        textSize = 16f
                        textColorResource = R.color.black
                        hintTextColor = R.color.colorText
                        backgroundResource = R.drawable.edit_text_states
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
        fun instance() = VerifyCodeUI()
    }
}