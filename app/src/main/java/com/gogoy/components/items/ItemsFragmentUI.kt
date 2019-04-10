package com.gogoy.components.items

import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class ItemsFragmentUI<T> : AnkoComponent<T> {

    lateinit var pbItems: ProgressBar
    lateinit var rvItems: RecyclerView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            id = R.id.rl_root
            backgroundColorResource = R.color.white

            pbItems = progressBar {
                id = R.id.pb_items
            }.lparams {
                centerInParent()
            }

            swipeRefreshLayout {
                id = R.id.swipe_refresh

                nestedScrollView {
                    lparams(width = matchParent, height = matchParent)

                    rvItems = recyclerView {
                        lparams(width = matchParent, height = wrapContent)
                        id = R.id.rv_item
                        layoutManager = GridLayoutManager(ctx, 2)
                    }
                }
            }
        }
    }
}