package com.gogoy.components.item

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.gogoy.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.design.appBarLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.themedTabLayout
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.viewPager

class ItemFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvItemRelated: RecyclerView
    lateinit var tabLayout: TabLayout
    lateinit var vpTab: ViewPager
    lateinit var tvTotalPerItem: TextView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            nestedScrollView {
                lparams(width = matchParent, height = matchParent)

                //container
                verticalLayout {
                    lparams(width = matchParent, height = wrapContent)

                    //image and name
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        //image
                        imageView {
                            id = R.id.iv_item_badge
                            imageResource = R.drawable.lombok_abang
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams(width = matchParent, height = dip(220))

                        //name, owner, price
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            elevation = 10f
                            gravity = Gravity.CENTER
                            padding = dip(10)
                            backgroundResource = R.drawable.border_shadow_whitebg

                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f

                                verticalLayout {
                                    textView {
                                        id = R.id.tv_item_name
                                        text = "Lombok Abang"
                                        gravity = Gravity.START
                                        textSize = 16f
                                        typeface = Typeface.DEFAULT_BOLD
                                        textColorResource = R.color.colorPrimary
                                    }.lparams {
                                        bottomMargin = dip(2.5f)
                                    }

                                    textView {
                                        id = R.id.tv_item_owner
                                        text = "PT Lombok"
                                        gravity = Gravity.START
                                        textSize = 11f
                                        textColorResource = R.color.colorText
                                    }.lparams {
                                        bottomMargin = dip(10f)
                                    }

                                    textView {
                                        id = R.id.tv_item_price
                                        text = "Rp.7000"
                                        gravity = Gravity.START
                                        textSize = 16f
                                        typeface = Typeface.DEFAULT_BOLD
                                        textColorResource = R.color.colorText
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                verticalLayout {
                                    //button min and plus
                                    linearLayout {
                                        lparams(width = matchParent, height = wrapContent)
                                        orientation = LinearLayout.HORIZONTAL
                                        gravity = Gravity.CENTER_HORIZONTAL

                                        button {
                                            id = R.id.bt_min
                                            textResource = R.string.sign_min
                                            textSize = 20f
                                            padding = 0
                                            gravity = Gravity.CENTER
                                            backgroundColorResource = R.color.colorPrimary

                                            onClick {
                                                if ((tvTotalPerItem.text).toString().toInt() > 1) {
                                                    tvTotalPerItem.text =
                                                        ((tvTotalPerItem.text).toString().toInt() - 1).toString()
                                                }
                                            }
                                        }.lparams(width = dip(30), height = dip(30))

                                        tvTotalPerItem = textView {
                                            id = R.id.tv_total_per_item
                                            text = "1"
                                            textSize = 22f
                                            textColorResource = R.color.colorText
                                        }.lparams {
                                            horizontalMargin = dip(10)
                                        }

                                        button {
                                            id = R.id.bt_plus
                                            textResource = R.string.sign_plus
                                            textSize = 20f
                                            padding = 0
                                            gravity = Gravity.CENTER
                                            backgroundColorResource = R.color.colorPrimary

                                            onClick {
                                                tvTotalPerItem.text =
                                                    ((tvTotalPerItem.text).toString().toInt() + 1).toString()
                                            }
                                        }.lparams(width = dip(30), height = dip(30))
                                    }

                                    //container action buy
                                    linearLayout {
                                        lparams(width = matchParent, height = wrapContent) {
                                            topMargin = dip(10)
                                        }
                                        gravity = Gravity.CENTER_HORIZONTAL

                                        //action buy
                                        linearLayout {
                                            lparams(width = dip(90), height = wrapContent)
                                            orientation = LinearLayout.HORIZONTAL
                                            gravity = Gravity.CENTER
                                            verticalPadding = dip(5)
                                            isClickable = true
                                            backgroundColorResource = R.color.colorPrimary

                                            onClick {
                                                //TODO: action beli
                                                toast("TODO: action beli")
                                            }

                                            imageView {
                                                imageResource = R.drawable.ic_shopping_cart_white_24dp
                                            }.lparams(width = dip(20), height = dip(20)) {
                                                horizontalMargin = dip(5)
                                            }

                                            textView {
                                                textSize = 12f
                                                textColorResource = R.color.white
                                                textResource = R.string.buy
                                            }
                                        }
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }

                        }.lparams(width = matchParent, height = dip(100)) {
                            centerInParent()
                            below(R.id.iv_item_badge)
                            topMargin = dip(-30)
                            horizontalMargin = dip(25)
                        }
                    }

                    //tab
                    verticalLayout {
                        lparams(matchParent, wrapContent) {
                            margin = dip(5)
                            topMargin = dip(20)
                        }
                        backgroundResource = R.drawable.border_shadow_whitebg

                        coordinatorLayout {
                            lparams(matchParent, matchParent)

                            appBarLayout {
                                lparams(matchParent, wrapContent) {
                                    topMargin = dip(1)
                                }
                                backgroundResource = R.drawable.border_shadow_whitebg

                                tabLayout = themedTabLayout(R.style.ThemeOverlay_AppCompat_Dark) {
                                    lparams(matchParent, wrapContent) {
                                        tabGravity = TabLayout.GRAVITY_FILL
                                        tabMode = TabLayout.MODE_FIXED
                                    }
                                    id = R.id.tab_layout
                                    tabTextColors = ColorStateList.valueOf(
                                        ContextCompat.getColor(context, R.color.colorText)
                                    )
                                }
                            }

                            vpTab = viewPager {
                                id = R.id.vp_tab
                            }.lparams(matchParent, matchParent)
                            (vpTab.layoutParams as CoordinatorLayout.LayoutParams).behavior =
                                AppBarLayout.ScrollingViewBehavior()
                        }

                        frameLayout {
                            lparams(width = matchParent, height = wrapContent)
                            id = R.id.fl_tab_content
                            horizontalPadding = dip(10)
                            verticalPadding = dip(20)
                        }
                    }

                    //header recycler item related
                    textView {
                        textResource = R.string.related_item
                        textSize = 14f
                        textColorResource = R.color.black
                    }.lparams {
                        horizontalMargin = dip(5)
                        topMargin = dip(20)
                    }

                    //recycler view item related
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                            bottomMargin = dip(20)
                        }

                        rvItemRelated = recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            id = R.id.rv_item_related
                            layoutManager = LinearLayoutManager(ctx, LinearLayout.HORIZONTAL, false)
                        }
                    }
                }
            }
        }
    }
}