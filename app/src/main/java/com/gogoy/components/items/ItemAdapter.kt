package com.gogoy.components.items

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.components.item.ItemActivity
import com.gogoy.data.models.ItemModel
import com.gogoy.utils.*
import kotlinx.coroutines.delay
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class ItemAdapter(
    private var context: Context,
    private var list: MutableList<ItemModel> = mutableListOf()
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        return ItemAdapter.ViewHolder(
            ItemAdapter.PartialUI().createView(
                AnkoContext.create(parent.context, parent)
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        val item = list[position]
        val activity = context as Activity
        val prefs = SharedPref(context)

        holder.tvName.text = item.name
        holder.tvPrice.text = toRupiah(item.price)
        holder.tvOwner.text = item.owner
        holder.ivBadge.setImageBitmap(decodeImageBase64ToBitmap(item.badge))

        //set state
        if (prefs.cartAmount(item.id) > 0) {
            holder.tvTotalPerItem.text = prefs.cartAmount(item.id).toString()
            holder.llBtnBuy.invisible()
            holder.llBtnMinPlus.visible()
        }

        holder.parent.onClick {
            activity.startActivity<ItemActivity>(
                Constant.UP_ID to item.id,
                Constant.UP_ITEM_NAME to item.name
            )
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        holder.parent.onLongClick {
            activity.startActivity<ItemActivity>(
                Constant.UP_ID to item.id,
                Constant.UP_ITEM_NAME to item.name
            )
            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        holder.llBtnBuy.onClick {
            holder.llBtnBuy.backgroundColorResource = R.color.colorPrimaryDark
            delay(50)
            holder.llBtnBuy.backgroundColorResource = R.color.colorPrimary

            prefs.cartAddItem(item.id to 1)

            //state when clicked btn buy
            holder.tvTotalPerItem.text = prefs.cartAmount(item.id).toString()
            holder.llBtnBuy.invisible()
            holder.llBtnMinPlus.visible()

            //update cart
            activity.invalidateOptionsMenu()
        }

        holder.btMin.onClick {
            holder.btMin.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            holder.btMin.backgroundColorResource = R.color.colorPrimary

            prefs.cartReduceAmountItem(item.id to prefs.cartAmount(item.id))

            if ((holder.tvTotalPerItem.text).toString().toInt() > 1) {
                holder.tvTotalPerItem.text = prefs.cartAmount(item.id).toString()
            } else {
                holder.llBtnMinPlus.invisible()
                holder.llBtnBuy.visible()
            }

            //update cart
            activity.invalidateOptionsMenu()
        }

        holder.btPlus.onClick {

            holder.btPlus.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            holder.btPlus.backgroundColorResource = R.color.colorPrimary

            prefs.cartAddAmountItem(item.id to prefs.cartAmount(item.id))

            //change state
            holder.tvTotalPerItem.text = prefs.cartAmount(item.id).toString()

            //update cart
            activity.invalidateOptionsMenu()
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_item_name)
        var tvPrice: TextView = view.find(R.id.tv_item_price)
        var tvOwner: TextView = view.find(R.id.tv_item_owner)
        //        var tvDimension: TextView = view.find(R.id.tv_item_dimension)
//        var tvDiscountPrice: TextView = view.find(R.id.tv_item_price_discount)
//        var tvCategoryName: TextView = view.find(R.id.tv_ctg_name)
//        var tvUnit: TextView = view.find(R.id.tv_item_unit)
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
            gridLayout {
                isColumnOrderPreserved = false
                alignmentMode = GridLayout.ALIGN_BOUNDS

                //container
                linearLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        horizontalMargin = dip(5)
                        verticalMargin = dip(10)
                    }
                    id = R.id.parent
                    orientation = LinearLayout.VERTICAL
                    elevation = 10f
                    isClickable = true
                    backgroundResource = R.drawable.border_flat_gray_whitebg

                    //image
                    imageView {
                        id = R.id.iv_item_badge
                        cropToPadding = true
                        backgroundResource = R.drawable.border_flat_gray_whitebg
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(width = matchParent, height = dip(150)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

                    view {
                        backgroundColorResource = R.color.colorPrimaryDark
                    }.lparams(width = matchParent, height = dip(2))

                    //text name, owner and price
                    verticalLayout {
                        lparams {
                            marginStart = dip(5)
                        }

                        textView {
                            id = R.id.tv_item_name
                            gravity = Gravity.START
                            textSize = 16f
                            typeface = Typeface.DEFAULT_BOLD
                            textColorResource = R.color.colorPrimary
                        }.lparams {
                            verticalMargin = dip(2.5f)
                        }

                        textView {
                            id = R.id.tv_item_owner
                            gravity = Gravity.START
                            textSize = 11f
                            textColorResource = R.color.colorText
                        }

                        textView {
                            id = R.id.tv_item_price
                            gravity = Gravity.START
                            textSize = 16f
                            typeface = Typeface.DEFAULT_BOLD
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