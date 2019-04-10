package com.gogoy.components.cart

import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class CartFragmentUI<T> : AnkoComponent<T> {

    lateinit var pbItemsCart: ProgressBar
    lateinit var rvCartItem: RecyclerView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white
            id = R.id.rl_root

            pbItemsCart = progressBar {
                id = R.id.pb_items_cart
            }.lparams {
                centerInParent()
            }

            swipeRefreshLayout {
                id = R.id.swipe_refresh

                nestedScrollView {
                    lparams(width = matchParent, height = matchParent)

                    relativeLayout {
                        lparams(width = matchParent, height = matchParent) {
                            horizontalMargin = dip(5)
                        }

                        rvCartItem = recyclerView {
                            lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(70)
                            }
                            id = R.id.rv_cart_item
                            layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                        }
                    }
                }
            }
        }
    }
}