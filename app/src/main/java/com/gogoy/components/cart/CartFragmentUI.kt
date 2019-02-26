package com.gogoy.components.cart

import android.graphics.Typeface
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

class CartFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvCartItem: RecyclerView
    lateinit var tvTotalBill: TextView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

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

            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                verticalPadding = dip(5)
                backgroundResource = R.drawable.border_rounded_gray_whitebg_toponly
                weightSum = 2f

                tvTotalBill = textView {
                    id = R.id.tv_total_bill
                    text = "Rp0 "
                    textSize = 20f
                    textColorResource = R.color.colorPrimary
                    typeface = Typeface.DEFAULT_BOLD
                }.lparams {
                    horizontalMargin = dip(5)
                    weight = 1f
                }

                button {
                    textResource = R.string.checkout
                    textColorResource = R.color.white
                    backgroundResource = R.drawable.border_color_primary
                }.lparams(width = dip(15), height = wrapContent) {
                    horizontalMargin = dip(5)
                    weight = 1f
                }
            }.lparams(width = matchParent, height = wrapContent) {
                alignParentBottom()
            }
        }
    }
}