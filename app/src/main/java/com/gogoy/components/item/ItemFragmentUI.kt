package com.gogoy.components.item

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.widget.*
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
import org.jetbrains.anko.support.v4.nestedScrollView
import org.jetbrains.anko.support.v4.viewPager

class ItemFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvItemRelated: RecyclerView
    private lateinit var tabLayout: TabLayout
    private lateinit var vpTab: ViewPager
    lateinit var pbItemsRelated: ProgressBar
    lateinit var tvItemName: TextView
    lateinit var tvItemOwner: TextView
    lateinit var tvItemPrice: TextView
    lateinit var ivItemBadge: ImageView
    lateinit var tvTotalPerItem: TextView
    lateinit var llBtnBuy: LinearLayout
    lateinit var llBtnMinPlus: LinearLayout
    lateinit var btnMin: Button
    lateinit var btnPlus: Button

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
                        ivItemBadge = imageView {
                            id = R.id.iv_item_badge
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams(width = matchParent, height = dip(220))

                        //name, owner, price
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            gravity = Gravity.CENTER
                            padding = dip(10)
                            backgroundResource = R.drawable.border_rounded_shadow_whitebg

                            linearLayout {
                                lparams(width = matchParent, height = wrapContent)
                                orientation = LinearLayout.HORIZONTAL
                                weightSum = 2f

                                verticalLayout {
                                    tvItemName = textView {
                                        id = R.id.tv_item_name
                                        gravity = Gravity.START
                                        textSize = 16f
                                        typeface = Typeface.DEFAULT_BOLD
                                        textColorResource = R.color.colorPrimary
                                        maxWidth = dip(180)
                                        maxLines = 2
                                        ellipsize = TextUtils.TruncateAt.END
                                    }.lparams {
                                        bottomMargin = dip(2.5f)
                                    }

                                    tvItemOwner = textView {
                                        id = R.id.tv_item_owner
                                        gravity = Gravity.START
                                        textSize = 11f
                                        textColorResource = R.color.colorText
                                        maxWidth = dip(180)
                                        maxLines = 1
                                        ellipsize = TextUtils.TruncateAt.END
                                    }.lparams {
                                        bottomMargin = dip(10f)
                                    }

                                    tvItemPrice = textView {
                                        id = R.id.tv_item_price
                                        gravity = Gravity.START
                                        textSize = 16f
                                        typeface = Typeface.DEFAULT_BOLD
                                        textColorResource = R.color.colorText
                                        maxWidth = dip(180)
                                        maxLines = 1
                                        ellipsize = TextUtils.TruncateAt.END
                                    }
                                }.lparams {
                                    weight = 1f
                                }

                                verticalLayout {
                                    gravity = Gravity.END

                                    relativeLayout {
                                        lparams(width = dip(100))

                                        //button min and plus
                                        llBtnMinPlus = linearLayout {
                                            lparams(width = matchParent, height = wrapContent)
                                            orientation = LinearLayout.HORIZONTAL
                                            gravity = Gravity.CENTER_VERTICAL or Gravity.END

                                            btnMin = button {
                                                id = R.id.bt_min
                                                textResource = R.string.sign_min
                                                textSize = 14f
                                                textColorResource = R.color.white
                                                padding = 0
                                                gravity = Gravity.CENTER
                                                backgroundColorResource = R.color.colorPrimary

                                            }.lparams(width = dip(25), height = dip(25))

                                            tvTotalPerItem = textView {
                                                id = R.id.tv_total_per_item
                                                textSize = 22f
                                                typeface = Typeface.DEFAULT_BOLD
                                                textColorResource = R.color.colorPrimary
                                            }.lparams {
                                                horizontalMargin = dip(10)
                                            }

                                            btnPlus = button {
                                                id = R.id.bt_plus
                                                textResource = R.string.sign_plus
                                                textSize = 14f
                                                textColorResource = R.color.white
                                                padding = 0
                                                gravity = Gravity.CENTER
                                                backgroundColorResource = R.color.colorPrimary

                                            }.lparams(width = dip(25), height = dip(25))
                                        }

                                        //container action buy
                                        linearLayout {
                                            lparams(width = matchParent, height = wrapContent)
                                            gravity = Gravity.END

                                            //action buy
                                            llBtnBuy = linearLayout {
                                                lparams(width = dip(90), height = wrapContent)
                                                orientation = LinearLayout.HORIZONTAL
                                                gravity = Gravity.CENTER
                                                verticalPadding = dip(5)
                                                isClickable = true
                                                backgroundColorResource = R.color.colorPrimary

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
                                    }
                                }.lparams {
                                    weight = 1f
                                }
                            }

                        }.lparams(width = matchParent, height = wrapContent) {
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
                        backgroundResource = R.drawable.border_flat_gray_whitebg

                        coordinatorLayout {
                            lparams(matchParent, matchParent)

                            appBarLayout {
                                lparams(matchParent, wrapContent) {
                                    topMargin = dip(1)
                                }
                                backgroundResource = R.drawable.border_flat_gray_whitebg

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

                        pbItemsRelated = progressBar {
                            id = R.id.pb_items_related
                        }.lparams {
                            centerInParent()
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