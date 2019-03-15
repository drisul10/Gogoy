package com.gogoy.components.account.settings.editprofile

import android.graphics.Typeface
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class EditProfileFragmentUI<T> : AnkoComponent<T> {

    lateinit var etDisplayName: EditText
    lateinit var etPhone: EditText
    lateinit var ivBadge: ImageView
    lateinit var btnBrowse: Button
    lateinit var btnSubmit: Button

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = matchParent)
            id = R.id.ll_root
            backgroundColorResource = R.color.white

            nestedScrollView {
                lparams(width = matchParent, height = matchParent)

                linearLayout {
                    lparams(width = matchParent, height = matchParent)
                    gravity = Gravity.CENTER

                    //container
                    verticalLayout {
                        lparams(width = dip(300), height = wrapContent)

                        verticalLayout {
                            lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(20)
                            }

                            ivBadge = imageView {
                                id = R.id.iv_user_badge
                                imageResource = R.drawable.osas
                                scaleType = ImageView.ScaleType.CENTER_CROP
                            }.lparams(width = dip(70), height = dip(70))

                            btnBrowse = button {
                                text = "browse"
                            }
                        }

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

                            etDisplayName = editText {
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

                            etPhone = editText {
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

                        //button submit
                        btnSubmit = button {
                            textResource = R.string.submit
                            textColorResource = R.color.white
                            backgroundResource = R.drawable.border_color_primary
                        }.lparams(width = matchParent, height = wrapContent)
                    }
                }
            }
        }
    }

    companion object {
        fun instance() = EditProfileFragmentUI<Fragment>()
    }
}