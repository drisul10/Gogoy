package com.gogoy.components.cart

import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.data.models.ItemModel
import com.gogoy.utils.toRupiah
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CartItemAdapter(
    private var list: ArrayList<ItemModel> = arrayListOf()
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private var totalPerItem = arrayListOf(0, 0, 0)
    private var totalBill: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvName.text = item.name
        holder.ivBadge.setImageResource(item.badge)
        holder.tvPrice.text = toRupiah(item.price.toDouble())

        holder.btMin.onClick {
            if ((holder.tvTotalPerItem.text).toString().toInt() > 1) {
                totalPerItem[position] = ((holder.tvTotalPerItem.text).toString().toInt() - 1)
                holder.tvTotalPerItem.text = totalPerItem[position].toString()
                Log.d("TOTAL_PER_ITEM", totalPerItem.toString())
            }
        }

        holder.btPlus.onClick {
            totalPerItem[position] = ((holder.tvTotalPerItem.text).toString().toInt() + 1)
            holder.tvTotalPerItem.text = totalPerItem[position].toString()
            Log.d("TOTAL_PER_ITEM", totalPerItem.toString())
        }

        totalBill += item.price.toInt()

        Log.d("TOTAL_BILL", totalBill.toString())
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_item_name)
        var ivBadge: ImageView = view.find(R.id.iv_item_badge)
        var tvPrice: TextView = view.find(R.id.tv_item_price)
        var tvTotalPerItem: TextView = view.find(R.id.tv_total_per_item)
        var btMin: Button = view.find(R.id.bt_min)
        var btPlus: Button = view.find(R.id.bt_plus)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent) {
                    verticalMargin = dip(10)
                }

                //container
                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    orientation = LinearLayout.HORIZONTAL
                    weightSum = 3f

                    linearLayout {
                        lparams(width = wrapContent, height = wrapContent) {
                            weight = 2f
                        }

                        //image
                        imageView {
                            id = R.id.iv_item_badge
                            scaleType = ImageView.ScaleType.CENTER_CROP
                        }.lparams(width = dip(80), height = dip(60)) {
                            marginEnd = dip(15)
                        }

                        //name and price
                        verticalLayout {
                            lparams(width = wrapContent, height = wrapContent) {
                                weight = 1f
                            }

                            textView {
                                id = R.id.tv_item_name
                                textSize = 16f
                                textColorResource = R.color.colorPrimary
                                typeface = Typeface.DEFAULT_BOLD
                            }

                            textView {
                                id = R.id.tv_item_price
                                textSize = 16f
                                textColorResource = R.color.colorText
                            }
                        }
                    }

                    //button min and plus
                    linearLayout {
                        lparams {
                            weight = 1f
                        }
                        orientation = LinearLayout.HORIZONTAL

                        button {
                            id = R.id.bt_min
                            textResource = R.string.sign_min
                            textSize = 20f
                            padding = 0
                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.colorPrimary
                        }.lparams(width = dip(30), height = dip(30))

                        textView {
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
                        }.lparams(width = dip(30), height = dip(30))
                    }
                }
            }
        }
    }
}