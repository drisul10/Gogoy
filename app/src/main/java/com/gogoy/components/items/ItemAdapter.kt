package com.gogoy.components.items

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.data.models.ItemModel
import com.gogoy.utils.toRupiah
import org.jetbrains.anko.*

class ItemAdapter(
    private var list: ArrayList<ItemModel> = arrayListOf()
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

        holder.tvName.text = item.name
        holder.tvPrice.text = toRupiah(item.price)
        holder.tvOwner.text = item.owner
        holder.ivBadge.setImageResource(item.badge)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_item_name)
        var tvPrice: TextView = view.find(R.id.tv_item_price)
        var tvOwner: TextView = view.find(R.id.tv_item_owner)
        var ivBadge: ImageView = view.find(R.id.iv_item_badge)
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

                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent) {
                            horizontalMargin = dip(30)
                            verticalMargin = dip(10)
                        }

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
                    }
                }
            }
        }
    }
}