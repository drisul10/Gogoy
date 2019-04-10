package com.gogoy.components.cart

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.components.item.ItemActivity
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.*
import kotlinx.coroutines.delay
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class CartGroupAdapter(
    private var context: Context,
    private var list: MutableList<ItemPromoModel> = mutableListOf(),
    val listenerOwnerId: (String) -> Unit,
    val listenerPrice: (Int) -> Unit
) : RecyclerView.Adapter<CartGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val activity = context as Activity
        val prefs = SharedPref(context)

        holder.tvName.text = item.name
        holder.ivBadge.setImageBitmap(decodeImageBase64ToBitmap(item.badge))
        holder.tvPrice.text = toRupiah(item.price)
        holder.tvTotalPerItem.text = prefs.cartAmount(item.id).toString()

        holder.parent.onClick {
            activity.startActivity<ItemActivity>(
                Constant.ACTIVITY_BEFORE to Constant.ACTIVITY_CART_BACK,
                Constant.UP_ID to item.id,
                Constant.UP_ITEM_NAME to item.name
            )
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        holder.parent.onLongClick {
            activity.startActivity<ItemActivity>(
                Constant.ACTIVITY_BEFORE to Constant.ACTIVITY_CART_BACK,
                Constant.UP_ID to item.id,
                Constant.UP_ITEM_NAME to item.name
            )
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        holder.btMin.onClick {
            holder.btMin.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            holder.btMin.backgroundColorResource = R.color.colorPrimary

            if ((holder.tvTotalPerItem.text).toString().toInt() > 1) {
                prefs.cartReduceAmountItem(item.id to prefs.cartAmount(item.id))
                holder.tvTotalPerItem.text = prefs.cartAmount(item.id).toString()
                listenerPrice(-(item.price))
            } else {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(R.string.confirm)

                alertDialog.setMessage("Apakah yakin menghapus item ${item.name} dari keranjang?")
                alertDialog.setPositiveButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
                alertDialog.setNegativeButton(R.string.del) { _, _ ->
                    prefs.cartReduceAmountItem(item.id to prefs.cartAmount(item.id))
                    list.removeAt(position)
                    notifyDataSetChanged()
                    if (list.size == 0) listenerOwnerId(item.owner_id)
                    listenerPrice(-(item.price))
                }

                //create and show dialog
                val dialog = alertDialog.create()
                dialog.show()
            }
        }

        holder.btPlus.onClick {
            holder.btPlus.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            holder.btPlus.backgroundColorResource = R.color.colorPrimary

            prefs.cartAddAmountItem(item.id to prefs.cartAmount(item.id))
            listenerPrice(item.price)

            //change state
            holder.tvTotalPerItem.text = prefs.cartAmount(item.id).toString()
        }

        if (prefs.cartAmount(item.id) == 0) {
            holder.parent.gone()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_item_name)
        var ivBadge: ImageView = view.find(R.id.iv_item_badge)
        var tvPrice: TextView = view.find(R.id.tv_item_price)
        var tvTotalPerItem: TextView = view.find(R.id.tv_total_per_item)
        var btMin: Button = view.find(R.id.bt_min)
        var btPlus: Button = view.find(R.id.bt_plus)
        val parent: LinearLayout = view.find(R.id.parent)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        verticalMargin = dip(10)
                    }
                    id = R.id.parent
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
                                maxWidth = dip(140)
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                                textColorResource = R.color.colorPrimary
                            }

                            textView {
                                id = R.id.tv_item_price
                                textSize = 16f
                                maxWidth = dip(140)
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