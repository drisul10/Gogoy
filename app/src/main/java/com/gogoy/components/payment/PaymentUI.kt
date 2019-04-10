package com.gogoy.components.payment

import android.graphics.Color
import androidx.appcompat.widget.Toolbar
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.themedToolbar

class PaymentUI : AnkoComponent<PaymentActivity> {

    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<PaymentActivity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            toolbar = themedToolbar {
                lparams(width = matchParent, height = wrapContent)
                elevation = 5f
                setTitleTextColor(Color.WHITE)
                setSubtitleTextColor(Color.WHITE)
                backgroundColorResource = R.color.colorPrimary
            }

            //frame for fragment
            frameLayout {
                lparams(width = matchParent, height = wrapContent)
                id = R.id.fl_main_content
            }
        }
    }

    companion object {
        fun instance() = PaymentUI()
    }
}