package com.gogoy.components.item

import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gogoy.R
import org.jetbrains.anko.*

class TabDescUI<T> : AnkoComponent<T> {

    lateinit var tvDesc: TextView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        linearLayout {
            tvDesc = textView {
                textSize = 16f
                textColorResource = R.color.colorText
            }
        }
    }

    companion object {
        fun instance() = TabDescUI<Fragment>()
    }
}