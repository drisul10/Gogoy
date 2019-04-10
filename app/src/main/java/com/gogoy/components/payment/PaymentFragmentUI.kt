package com.gogoy.components.payment

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.nestedScrollView

class PaymentFragmentUI<T> : AnkoComponent<T> {

    lateinit var rvItem: RecyclerView

    override fun createView(ui: AnkoContext<T>) = with(ui) {
        relativeLayout {
            lparams(width = matchParent, height = matchParent)
            backgroundColorResource = R.color.white

            nestedScrollView {
                lparams(width = matchParent, height = matchParent)

                verticalLayout {
                    lparams(width = matchParent, height = matchParent)

                    cardView {
                        lparams(height = wrapContent, width = matchParent) {
                            horizontalMargin = dip(10)
                            verticalMargin = dip(5)
                        }

                        verticalLayout {
                            lparams(height = wrapContent, width = matchParent) {
                                margin = dip(5)
                            }

                            textView {
                                textResource = R.string.send_address
                                textSize = 16f
                            }.lparams {
                                bottomMargin = dip(10)
                            }

                            textView {
                                text =
                                    "Sorrow childe soon charms sighed was reverie one ear soon, eros her was like along maidens dares but,  who mighty at seek they chill soul waste seemed, he my and lands childe if would the save, himnot these grace of of once for a than. Or in of in made olden where were, and within save their from. Womans he saw strength lyres moths knew calm. Ive it third in nor and flaunting it relief before, earthly fame taste than lay mote his he girls seemed. Lines weary all nor scene delight he say. Girls of the still his."
                                textSize = 12f
                            }
                        }
                    }

                    cardView {
                        lparams(height = wrapContent, width = matchParent) {
                            horizontalMargin = dip(10)
                            verticalMargin = dip(5)
                        }

                        verticalLayout {
                            lparams(height = wrapContent, width = matchParent) {
                                margin = dip(5)
                            }

                            textView {
                                textResource = R.string.payment_method
                                textSize = 16f
                            }.lparams {
                                bottomMargin = dip(10)
                            }

                            textView {
                                text =
                                    "Sorrow childe soon charms sighed was reverie one ear soon, eros her was like along maidens dares but,  who mighty at seek they chill soul waste seemed, he my and lands childe if would the save, himnot these grace of of once for a than. Or in of in made olden where were, and within save their from. Womans he saw strength lyres moths knew calm. Ive it third in nor and flaunting it relief before, earthly fame taste than lay mote his he girls seemed. Lines weary all nor scene delight he say. Girls of the still his."
                                textSize = 12f
                            }
                        }
                    }

                    cardView {
                        lparams(height = wrapContent, width = matchParent) {
                            horizontalMargin = dip(10)
                            verticalMargin = dip(5)
                        }

                        verticalLayout {
                            lparams(height = wrapContent, width = matchParent) {
                                margin = dip(5)
                            }

                            textView {
                                textResource = R.string.shopping_list
                                textSize = 16f
                            }.lparams {
                                bottomMargin = dip(10)
                            }

                            relativeLayout {
                                lparams(width = matchParent, height = matchParent)

                                rvItem = recyclerView {
                                    lparams(width = matchParent, height = wrapContent)
                                    id = R.id.rv_cart_item
                                    layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                                }
                            }
                        }
                    }

                    cardView {
                        lparams(height = wrapContent, width = matchParent) {
                            horizontalMargin = dip(10)
                            verticalMargin = dip(5)
                        }

                        verticalLayout {
                            lparams(height = wrapContent, width = matchParent) {
                                margin = dip(5)
                            }

                            textView {
                                textResource = R.string.cost_detail
                                textSize = 16f
                            }.lparams {
                                bottomMargin = dip(10)
                            }

                            textView {
                                text =
                                    "Sorrow childe soon charms sighed was reverie one ear soon, eros her was like along maidens dares but,  who mighty at seek they chill soul waste seemed, he my and lands childe if would the save, himnot these grace of of once for a than. Or in of in made olden where were, and within save their from. Womans he saw strength lyres moths knew calm. Ive it third in nor and flaunting it relief before, earthly fame taste than lay mote his he girls seemed. Lines weary all nor scene delight he say. Girls of the still his."
                                textSize = 12f
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun instance() = PaymentFragmentUI<Fragment>()
    }
}