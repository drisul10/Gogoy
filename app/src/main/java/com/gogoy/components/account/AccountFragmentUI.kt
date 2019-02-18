package com.gogoy.components.account

import android.graphics.Typeface
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

class AccountFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvMenuAccount: RecyclerView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
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

                    //TODO: rounded image
                    imageView {
                        imageResource = R.drawable.osas
                        backgroundResource = R.drawable.border_color_primary
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(width = dip(80), height = dip(80)) {
                        marginEnd = dip(15)
                    }

                    verticalLayout {
                        lparams(width = matchParent, height = wrapContent)

                        textView {
                            textResource = R.string.dummy_people_name
                            textSize = 20f
                            textColorResource = R.color.colorText
                            typeface = Typeface.DEFAULT_BOLD
                        }

                        textView {
                            textResource = R.string.verified_account
                            textSize = 12f
                            textColorResource = R.color.colorText
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