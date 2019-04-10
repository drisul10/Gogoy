package com.gogoy.components.account

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

class AccountFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvMenuAccount: RecyclerView
    lateinit var tvUserDisplayName: TextView
    lateinit var ivBadge: ImageView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            id = R.id.rl_root
            backgroundColorResource = R.color.white

            //container
            verticalLayout {
                lparams(width = matchParent, height = wrapContent)

                //photo profile and name
                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        margin = dip(20)
                    }
                    orientation = LinearLayout.HORIZONTAL
                    backgroundResource = R.drawable.circular_50

                    ivBadge = imageView {
                        scaleType = ImageView.ScaleType.CENTER_CROP
                        clipToOutline = true
                        backgroundResource = R.drawable.circular_50
                    }.lparams(width = dip(70), height = dip(70)) {
                        marginEnd = dip(15)
                    }

                    verticalLayout {
                        lparams(width = matchParent, height = wrapContent)

                        tvUserDisplayName = textView {
                            textSize = 20f
                            textColorResource = R.color.colorText
                            typeface = Typeface.DEFAULT_BOLD
                        }

                        textView {
                            textResource = R.string.lorem_ipsum
                        }
                    }
                }

                view {
                    backgroundColorResource = R.color.colorBorder
                }.lparams(width = matchParent, height = dip(1))

                //menu account
                nestedScrollView {
                    lparams(width = matchParent, height = matchParent)

                    relativeLayout {
                        lparams(width = matchParent, height = matchParent)

                        rvMenuAccount = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            id = R.id.rv_menu_account
                            layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                        }
                    }
                }
            }
        }
    }
}