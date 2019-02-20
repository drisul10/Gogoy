package com.gogoy.components.main

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.components.items.ItemsActivity
import com.gogoy.data.models.CategoryModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class CategoryAdapter(
    private var list: ArrayList<CategoryModel> = arrayListOf()
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvName.text = item.name
        holder.ivBadge.setImageResource(item.badge)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_ctg_name)
        var ivBadge: ImageView = view.find(R.id.iv_ctg_badge)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            gridLayout {
                isColumnOrderPreserved = false
                alignmentMode = GridLayout.ALIGN_BOUNDS

                verticalLayout {
                    lparams(width = matchParent, height = dip(80))
                    gravity = Gravity.CENTER
                    isClickable = true
                    backgroundResource = R.drawable.border_rounded_gray_nobg_noright

                    onClick {
                        startActivity<ItemsActivity>()
                    }

                    imageView {
                        id = R.id.iv_ctg_badge
                    }.lparams(width = dip(35), height = dip(35))

                    textView {
                        id = R.id.tv_ctg_name
                        gravity = Gravity.CENTER
                        textColorResource = R.color.colorText
                    }
                }
            }
        }
    }
}