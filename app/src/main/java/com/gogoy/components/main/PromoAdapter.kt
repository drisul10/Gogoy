package com.gogoy.components.main

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.data.models.ItemModel
import com.gogoy.utils.toRupiah
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class PromoAdapter(
    private var list: ArrayList<ItemModel> = arrayListOf()
) : RecyclerView.Adapter<PromoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvName.text = item.name
        holder.tvPrice.text = toRupiah(item.price.toDouble())
        holder.tvOwner.text = item.owner
        holder.ivBadge.setImageResource(item.badge)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_item_name)
        var tvPrice: TextView = view.find(R.id.tv_item_price)
        var tvOwner: TextView = view.find(R.id.tv_item_owner)
        var ivBadge: ImageView = view.find(R.id.iv_item_badge)
    }

    private class ItemUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = wrapContent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                //container
                linearLayout {
                    lparams(width = dip(140), height = dip(173)) {
                        horizontalMargin = dip(5)
                    }
                    orientation = LinearLayout.VERTICAL
                    isClickable = true
                    backgroundResource = R.drawable.border_flat_gray_nobg

                    onClick {
                        //TODO: detail
                        toast("TODO: detail")
                    }

                    //image
                    imageView {
                        id = R.id.iv_item_badge
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(width = matchParent, height = dip(70)) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }

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
                        }.lparams {
                            verticalMargin = dip(2.5f)
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

                    //action buy
                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
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
            }
        }
    }
}