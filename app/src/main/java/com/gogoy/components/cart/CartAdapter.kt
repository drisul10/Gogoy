package com.gogoy.components.cart

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.components.payment.PaymentActivity
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.Constant
import com.gogoy.utils.SharedPref
import com.gogoy.utils.gone
import com.gogoy.utils.toRupiah
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.sdk27.coroutines.onClick

class CartAdapter(
    private var context: Context,
    private var list: MutableList<ItemPromoModel> = mutableListOf()
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var arrIdByGroup: ArrayList<ArrayList<String>> = arrayListOf()
    private var arrPriceByGroup: ArrayList<ArrayList<Int>> = arrayListOf()
    private var byOwner = list.groupBy { it.owner_id }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = context as Activity
        val listByOwner = byOwner.values.toMutableList()
        val sharedPref = SharedPref(context)

        if (position < listByOwner.size) {
            val adapter = CartGroupAdapter(context, listByOwner[position] as MutableList<ItemPromoModel>, {
                if (listByOwner[position].isEmpty()) holder.parent.gone()
            }) {
                arrPriceByGroup[position].add(it)
                holder.tvSubtotal.text = toRupiah(arrPriceByGroup[position].sum())
            }

            holder.rvItemGroup.adapter = adapter

            val btn = Button(context)
            val lyt = RelativeLayout.LayoutParams(240, 120)
            btn.layoutParams = lyt
            btn.backgroundResource = R.drawable.border_rounded_shadow_bluebg
            btn.id = position
            btn.textColorResource = R.color.white
            btn.textResource = R.string.pay

            holder.llPayment.addView(btn)

            listByOwner[position].forEach {
                if (position >= arrIdByGroup.size) {
                    arrIdByGroup.add(position, arrayListOf(it.id))
                    arrPriceByGroup.add(position, arrayListOf(it.price * sharedPref.cartAmount(it.id)))
                } else {
                    arrIdByGroup[position].add(it.id)
                    arrPriceByGroup[position].add(it.price * sharedPref.cartAmount(it.id))
                }
            }

            btn.onClick {
                activity.startActivity<PaymentActivity>(
                    Constant.ACTIVITY_BEFORE to Constant.ACTIVITY_CART,
                    Constant.UP_IDS to arrIdByGroup[position]
                )
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
            }

            holder.tvSubtotal.text = toRupiah(arrPriceByGroup[position].sum())
        } else {
            holder.parent.gone()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSubtotal: TextView = view.find(R.id.tv_subtotal)
        val parent: LinearLayout = view.find(R.id.parent)
        var llPayment: LinearLayout = view.find(R.id.ll_wrapper_bt_payment)
        val rvItemGroup: RecyclerView = view.find(R.id.rv_item_group)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        padding = dip(10)
                        verticalMargin = dip(10)
                    }
                    backgroundResource = R.drawable.border_flat_gray_whitebg
                    elevation = 10f
                    id = R.id.parent
                    orientation = LinearLayout.VERTICAL

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        recyclerView {
                            lparams(width = matchParent, height = wrapContent)
                            id = R.id.rv_item_group
                            layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)
                        }
                    }

                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        gravity = Gravity.CENTER

                        linearLayout {
                            textView {
                                id = R.id.tv_subtotal
                                gravity = Gravity.START
                                textSize = 16f
                                typeface = Typeface.DEFAULT_BOLD
                                textColorResource = R.color.colorText
                            }
                        }.lparams {
                            weight = 1f
                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            id = R.id.ll_wrapper_bt_payment
                            gravity = Gravity.END
                        }.lparams {
                            weight = 1f
                        }
                    }
                }
            }
        }
    }
}