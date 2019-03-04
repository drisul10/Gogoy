package com.gogoy.components.main

import android.app.Activity
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
import com.gogoy.components.item.ItemActivity
import com.gogoy.data.models.ItemCartModel
import com.gogoy.data.models.ItemModel
import com.gogoy.utils.Prefs
import com.gogoy.utils.invisible
import com.gogoy.utils.toRupiah
import com.gogoy.utils.visible
import kotlinx.coroutines.delay
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class BestSellerAdapter(
    private var context: Context,
    private var list: ArrayList<ItemModel> = arrayListOf(),
    val listener: (Int) -> Unit
) : RecyclerView.Adapter<BestSellerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.parent.onClick {
            val activity = context as Activity

            activity.startActivity<ItemActivity>(
                "ACTIVITY_ORIGIN" to "MAIN",
                "ID" to item.id,
                "NAME" to item.name,
                "OWNER" to item.owner,
                "PRICE" to item.price,
                "BADGE" to item.badge
            )
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)

            holder.parent.backgroundResource = R.drawable.border_rounded_shadow_graybg
            delay(150)
            holder.parent.backgroundResource = R.drawable.border_rounded_shadow_whitebg
        }

        holder.parent.onLongClick {
            val activity = context as Activity

            activity.startActivity<ItemActivity>(
                "ACTIVITY_ORIGIN" to "MAIN",
                "ID" to item.id,
                "NAME" to item.name,
                "OWNER" to item.owner,
                "PRICE" to item.price,
                "BADGE" to item.badge
            )
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)

            holder.parent.backgroundResource = R.drawable.border_rounded_shadow_graybg
            delay(50)
            holder.parent.backgroundResource = R.drawable.border_rounded_shadow_whitebg
        }

        holder.tvName.text = item.name
        holder.tvPrice.text = toRupiah(item.price)
        holder.tvOwner.text = item.owner
        holder.ivBadge.setImageResource(item.badge)

        val prefs = Prefs(context)
        var listItem = prefs.getPref()
        var totalPerItem = 0
        var indexListItem = 0

        // get current totalPerItem when data is exist
        // and then store the index into indexListItem
        for ((j, i) in listItem.withIndex()) {
            if (i.id == item.id) {
                totalPerItem = i.totalPerItem
                indexListItem = j
            }
        }

        //set state
        if (totalPerItem > 0) {
            holder.tvTotalPerItem.text = totalPerItem.toString()
            holder.llBtnBuy.invisible()
            holder.llBtnMinPlus.visible()
        }

        holder.llBtnBuy.onClick {

            holder.llBtnBuy.backgroundColorResource = R.color.colorPrimaryDark
            delay(50)
            holder.llBtnBuy.backgroundColorResource = R.color.colorPrimary

            //keep listItem up to date
            listItem = prefs.getPref()
            totalPerItem = 1

            //update listener fragment
            listener(listItem.size + 1)

            //check if sharedPref size 0 or data is exist then add new item list to sharedPref
            if (prefs.getPref().size == 0 || !prefs.existId(item.id)) {
                listItem.add(ItemCartModel(item.id, item.name, item.price, item.owner, item.badge, totalPerItem))
                prefs.setPref(listItem)
            }

            //state when clicked btn buy
            holder.tvTotalPerItem.text = totalPerItem.toString()
            holder.llBtnBuy.invisible()
            holder.llBtnMinPlus.visible()
        }

        holder.btMin.onClick {

            holder.btMin.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            holder.btMin.backgroundColorResource = R.color.colorPrimary

            //keep listItem up to date
            listItem = prefs.getPref()

            for ((j, i) in listItem.withIndex()) {
                if (i.id == item.id) {
                    totalPerItem = i.totalPerItem
                    indexListItem = j
                }
            }

            if ((holder.tvTotalPerItem.text).toString().toInt() > 1) {
                listItem[indexListItem].totalPerItem -= 1
                prefs.setPref(listItem)

                //change state
                holder.tvTotalPerItem.text = listItem[indexListItem].totalPerItem.toString()

                //update adapter
                notifyDataSetChanged()
            } else {
                listItem.removeIf { s -> s.id == item.id }
                prefs.setPref(listItem)

                //update listener fragment
                listener(listItem.size)

                //hide layout btn min&plus and show layout btn buy
                holder.llBtnMinPlus.invisible()
                holder.llBtnBuy.visible()

                //update adapter
                notifyDataSetChanged()
            }
        }

        holder.btPlus.onClick {

            holder.btPlus.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            holder.btPlus.backgroundColorResource = R.color.colorPrimary

            //keep listItem up to date
            listItem = prefs.getPref()

            // get current totalPerItem when data is exist
            // and then store the index into indexListItem
            for ((j, i) in listItem.withIndex()) {
                if (i.id == item.id) {
                    totalPerItem = i.totalPerItem
                    indexListItem = j
                }
            }

            listItem[indexListItem].totalPerItem += 1
            prefs.setPref(listItem)

            //change state
            holder.tvTotalPerItem.text = listItem[indexListItem].totalPerItem.toString()

            //update adapter
            notifyDataSetChanged()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_item_name)
        var tvPrice: TextView = view.find(R.id.tv_item_price)
        var tvOwner: TextView = view.find(R.id.tv_item_owner)
        var ivBadge: ImageView = view.find(R.id.iv_item_badge)
        var llBtnBuy: LinearLayout = view.find(R.id.ll_btn_buy)
        var llBtnMinPlus: LinearLayout = view.find(R.id.ll_btn_min_plus)
        var tvTotalPerItem: TextView = view.find(R.id.tv_total_per_item)
        var btMin: Button = view.find(R.id.bt_min)
        var btPlus: Button = view.find(R.id.bt_plus)
        val parent: LinearLayout = view.find(R.id.parent)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = wrapContent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                //container
                linearLayout {
                    lparams(width = dip(150), height = wrapContent) {
                        horizontalMargin = dip(5)
                    }
                    id = R.id.parent
                    orientation = LinearLayout.VERTICAL
                    isClickable = true
                    backgroundResource = R.drawable.border_rounded_shadow_whitebg

                    //image
                    imageView {
                        id = R.id.iv_item_badge
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(width = dip(130), height = dip(90)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                        margin = dip(10)
                    }

                    //text name, owner and price
                    verticalLayout {
                        lparams {
                            horizontalMargin = dip(10)
                        }

                        textView {
                            id = R.id.tv_item_name
                            gravity = Gravity.START
                            textSize = 16f
                            typeface = Typeface.DEFAULT_BOLD
                            maxWidth = dip(130)
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                            textColorResource = R.color.colorPrimary
                        }.lparams {
                            verticalMargin = dip(2.5f)
                        }

                        textView {
                            id = R.id.tv_item_owner
                            gravity = Gravity.START
                            textSize = 11f
                            maxWidth = dip(130)
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                            textColorResource = R.color.colorText
                        }

                        textView {
                            id = R.id.tv_item_price
                            gravity = Gravity.START
                            textSize = 16f
                            typeface = Typeface.DEFAULT_BOLD
                            maxWidth = dip(130)
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                            textColorResource = R.color.colorText
                        }.lparams {
                            verticalMargin = dip(2.5f)
                        }
                    }

                    //container action buy & button min and plus
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent) {
                            margin = dip(10)
                        }
                        isClickable = true

                        //action buy
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            id = R.id.ll_btn_buy
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER
                            verticalPadding = dip(5)
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

                        //button min and plus
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            id = R.id.ll_btn_min_plus
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            button {
                                id = R.id.bt_min
                                textResource = R.string.sign_min
                                textSize = 14f
                                textColorResource = R.color.white
                                padding = 0
                                gravity = Gravity.CENTER
                                backgroundColorResource = R.color.colorPrimary
                            }.lparams(width = dip(25), height = dip(25))

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
                                textSize = 14f
                                textColorResource = R.color.white
                                padding = 0
                                gravity = Gravity.CENTER
                                backgroundColorResource = R.color.colorPrimary
                            }.lparams(width = dip(25), height = dip(25))
                        }.invisible()
                    }
                }
            }
        }
    }
}