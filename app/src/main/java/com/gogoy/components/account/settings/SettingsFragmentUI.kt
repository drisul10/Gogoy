package com.gogoy.components.account.settings

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

class SettingsFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvMenuSettings: RecyclerView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            id = R.id.rl_root
            backgroundColorResource = R.color.white

            //container
            verticalLayout {
                lparams(width = matchParent, height = wrapContent)

                nestedScrollView {
                    lparams(width = matchParent, height = matchParent)

                    relativeLayout {
                        lparams(width = matchParent, height = matchParent)

                        rvMenuSettings = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                        }
                    }
                }
            }
        }
    }
}