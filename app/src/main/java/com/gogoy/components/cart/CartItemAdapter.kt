package com.gogoy.components.cart

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.data.models.ItemCartModel
import com.gogoy.utils.Prefs
import com.gogoy.utils.toRupiah
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CartItemAdapter(
    private var context: Context,
    private var list: ArrayList<ItemCartModel> = arrayListOf(),
    val listener: (Int) -> Unit
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    private var totalBill: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val prefs = Prefs(context)
        var listItem = prefs.getPref()
        var indexListItem = 0

        for ((j, i) in listItem.withIndex()) {
            if (i.id == item.id) indexListItem = j
        }

        holder.tvName.text = item.name
        holder.ivBadge.setImageResource(item.badge)
        holder.tvPrice.text = toRupiah(item.price)
        holder.tvTotalPerItem.text = listItem[indexListItem].totalPerItem.toString()

        //set total bill and send to fragment
        totalBill += item.price * listItem[indexListItem].totalPerItem
        listener(totalBill)

        holder.btMin.onClick {
            listItem = prefs.getPref()

            if ((holder.tvTotalPerItem.text).toString().toInt() > 1) {
                listItem[indexListItem].totalPerItem -= 1
                prefs.setPref(listItem)

                //set state tvTotalPerItem
                holder.tvTotalPerItem.text = ((holder.tvTotalPerItem.text).toString().toInt() - 1).toString()

                //set total bill and send to fragment
                totalBill -= item.price
                listener(totalBill)
            } else {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(R.string.confirm)

                val message: String = item.name
                alertDialog.setMessage("Apakah yakin menghapus item $message dari keranjang?")
                alertDialog.setPositiveButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
                alertDialog.setNegativeButton(R.string.del) { _, _ ->
                    listItem.removeIf { s -> s.id == item.id }
                    prefs.setPref(listItem)

                    //just for refresh recyclerView
                    val activity = context as Activity
                    activity.startActivity(context.intentFor<CartActivity>("ACTIVITY_ORIGIN" to "MAIN").clearTask().newTask())
                    activity.overridePendingTransition(R.anim.blink, R.anim.blink)
                }

                //create and show dialog
                val dialog = alertDialog.create()
                dialog.show()
            }
        }

        holder.btPlus.onClick {
            listItem = prefs.getPref()

            listItem[indexListItem].totalPerItem += 1
            prefs.setPref(listItem)

            //set state tvTotalPerItem
            holder.tvTotalPerItem.text = listItem[indexListItem].totalPerItem.toString()

            //set total bill and send to fragment
            totalBill += item.price
            listener(totalBill)
        }
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
                lparams(width = matchParent, height = wrapContent)

                //container
                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        verticalMargin = dip(10)
                    }
                    id = R.id.ll_container
                    orientation = LinearLayout.HORIZONTAL

                    linearLayout {
                        lparams(width = wrapContent, height = wrapContent)

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
                                typeface = Typeface.DEFAULT_BOLD
                                maxWidth = dip(160)
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                                textColorResource = R.color.colorPrimary
                            }

                            textView {
                                id = R.id.tv_item_price
                                textSize = 16f
                                maxWidth = dip(160)
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                                textColorResource = R.color.colorText
                            }
                        }
                    }

                    //button min and plus
                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.END

                        button {
                            id = R.id.bt_min
                            textResource = R.string.sign_min
                            textSize = 20f
                            textColorResource = R.color.white
                            padding = 0
                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.colorPrimary
                        }.lparams(width = dip(30), height = dip(30))

                        textView {
                            id = R.id.tv_total_per_item
                            textSize = 22f
                            typeface = Typeface.DEFAULT_BOLD
                            textColorResource = R.color.colorPrimary
                        }.lparams {
                            horizontalMargin = dip(10)
                        }

                        button {
                            id = R.id.bt_plus
                            textResource = R.string.sign_plus
                            textSize = 20f
                            textColorResource = R.color.white
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