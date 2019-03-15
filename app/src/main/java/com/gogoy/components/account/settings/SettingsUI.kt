package com.gogoy.components.account.settings

import android.graphics.Color
import android.widget.Toolbar
import com.gogoy.R
import org.jetbrains.anko.*

class SettingsUI : AnkoComponent<SettingsActivity> {

    lateinit var toolbar: Toolbar

    override fun createView(ui: AnkoContext<SettingsActivity>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            toolbar = toolbar {
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
}