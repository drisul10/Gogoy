package com.gogoy.components.items

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

class ItemsFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvItem: RecyclerView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            nestedScrollView {
                lparams(width = matchParent, height = matchParent)

                rvItem = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.rv_item
                    layoutManager = GridLayoutManager(ctx, 2)
                }
            }
        }
    }
}