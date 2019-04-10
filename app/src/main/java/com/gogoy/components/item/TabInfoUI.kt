package com.gogoy.components.item

import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gogoy.R
import org.jetbrains.anko.*

class TabInfoUI<T> : AnkoComponent<T> {

    lateinit var tvInfo: TextView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        linearLayout {
            tvInfo = textView {
                textSize = 16f
                textColorResource = R.color.colorText
            }
        }
    }

    companion object {
        fun instance() = TabInfoUI<Fragment>()
    }
}