package com.gogoy.components.main

import android.graphics.Typeface
import android.view.Gravity
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick

class MainFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvBanner: RecyclerView
    lateinit var rvCategory: RecyclerView
    lateinit var rvPromo: RecyclerView
    lateinit var rvBestSeller: RecyclerView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = wrapContent)

            //container location dropdown
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)
                gravity = Gravity.CENTER

                //location dropdown
                //TODO: dot indicator
                linearLayout {
                    lparams(width = wrapContent, height = wrapContent) {
                        topMargin = dip(20)
                    }
                    orientation = LinearLayout.HORIZONTAL
                    verticalPadding = dip(5)
                    horizontalPadding = dip(15)
                    backgroundResource = R.drawable.border_rounded_gray_nobg

                    textView {
                        textResource = R.string.location_send
                        textSize = 14f
                        textColorResource = R.color.colorText
                    }.lparams {
                        marginEnd = dip(20)
                    }

                    textView {
                        textResource = R.string.trucuk
                        textSize = 14f
                        textColorResource = R.color.colorText
                    }

                    //TODO : ambil data dari array
                    spinner {}
                }
            }

            //recycler view banner
            relativeLayout {
                lparams(width = matchParent, height = wrapContent) {
                    topMargin = dip(20)
                }

                rvBanner = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.rv_banner
                    layoutManager = LinearLayoutManager(ctx, LinearLayout.HORIZONTAL, false)
                }
            }

            //header recycler view category
            textView {
                textResource = R.string.category
                textSize = 14f
                textColorResource = R.color.black
            }.lparams {
                horizontalMargin = dip(5)
                topMargin = dip(20)
                bottomMargin = dip(10)
            }

            //recycler view category
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                rvCategory = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.rv_category
                    layoutManager = GridLayoutManager(ctx, 3)
                }
            }

            //header recycler view promo
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    horizontalMargin = dip(10)
                    topMargin = dip(20)
                    bottomMargin = dip(10)
                    weightSum = 2f
                }
                orientation = LinearLayout.HORIZONTAL

                textView {
                    gravity = Gravity.START
                    textResource = R.string.promo
                    textSize = 14f
                    textColorResource = R.color.black
                }.lparams {
                    weight = 1f
                }

                textView {
                    gravity = Gravity.END
                    textResource = R.string.view_all
                    textColorResource = R.color.blueDeepSky
                    typeface = Typeface.DEFAULT_BOLD
                    isClickable = true

                    onClick {
                        //TODO: lihat semua
                        toast("TODO: lihat semua")
                    }
                }.lparams {
                    weight = 1f
                }
            }

            //recycler view promo
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                rvPromo = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.rv_promo
                    layoutManager = LinearLayoutManager(ctx, LinearLayout.HORIZONTAL, false)
                }
            }

            //header recycler view promo
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    horizontalMargin = dip(10)
                    topMargin = dip(20)
                    bottomMargin = dip(10)
                    weightSum = 2f
                }
                orientation = LinearLayout.HORIZONTAL

                textView {
                    gravity = Gravity.START
                    textResource = R.string.best_seller
                    textSize = 14f
                    textColorResource = R.color.black
                }.lparams {
                    weight = 1f
                }

                textView {
                    gravity = Gravity.END
                    textResource = R.string.view_all
                    textColorResource = R.color.blueDeepSky
                    typeface = Typeface.DEFAULT_BOLD
                    isClickable = true

                    onClick {
                        //TODO: lihat semua
                        toast("TODO: lihat semua")
                    }
                }.lparams {
                    weight = 1f
                }
            }

            //recycler view best seller
            relativeLayout {
                lparams(width = matchParent, height = wrapContent)

                rvBestSeller = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.rv_best_seller
                    layoutManager = LinearLayoutManager(ctx, LinearLayout.HORIZONTAL, false)
                }
            }

            //only pusher
            view {}.lparams {
                margin = dip(20)
            }
        }
    }
}