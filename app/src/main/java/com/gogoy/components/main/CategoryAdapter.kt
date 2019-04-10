package com.gogoy.components.main

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.components.bengkel.BengkelActivity
import com.gogoy.components.items.ItemsActivity
import com.gogoy.data.models.CategoryModel
import com.gogoy.utils.Constant
import kotlinx.coroutines.delay
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.sdk27.coroutines.onLongClick

class CategoryAdapter(
    private var context: Context,
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

        holder.container.onClick {
            val activity = context as Activity

            when (item.id) {
                "1" -> activity.startActivity<ItemsActivity>(
                    Constant.UP_TYPE_ID to Constant.ID_ITEM_ALL_KOMODITI,
                    Constant.UP_TITLE to item.name
                )
                "2" -> activity.startActivity<ItemsActivity>(
                    Constant.UP_TYPE_ID to Constant.ID_ITEM_ALL_SAYUR,
                    Constant.UP_TITLE to item.name
                )
                "3" -> activity.startActivity<BengkelActivity>("ID" to item.id, "NAME" to item.name)
            }

            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)

            holder.wrapperBadge.backgroundResource = R.drawable.border_rounded_shadow_graybg
            delay(150)
            holder.wrapperBadge.backgroundResource = R.drawable.border_rounded_shadow_whitebg
        }

        holder.container.onLongClick {
            val activity = context as Activity

            when (item.id) {
                "1" -> activity.startActivity<ItemsActivity>("ID" to item.id, "NAME" to item.name)
                "2" -> activity.startActivity<ItemsActivity>("ID" to item.id, "NAME" to item.name)
                "3" -> activity.startActivity<BengkelActivity>("ID" to item.id, "NAME" to item.name)
            }

            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)

            holder.wrapperBadge.backgroundResource = R.drawable.border_rounded_shadow_graybg
            delay(50)
            holder.wrapperBadge.backgroundResource = R.drawable.border_rounded_shadow_whitebg
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_ctg_name)
        var ivBadge: ImageView = view.find(R.id.iv_ctg_badge)
        var container: LinearLayout = view.find(R.id.container)
        var wrapperBadge: RelativeLayout = view.find(R.id.rl_wrapper_badge)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            gridLayout {
                isColumnOrderPreserved = false
                alignmentMode = GridLayout.ALIGN_BOUNDS

                verticalLayout {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.container
                    gravity = Gravity.CENTER
                    isClickable = true

                    relativeLayout {
                        lparams(width = dip(85), height = dip(85)) {
                            margin = dip(5)
                        }
                        id = R.id.rl_wrapper_badge
                        gravity = Gravity.CENTER
                        backgroundResource = R.drawable.border_rounded_shadow_whitebg

                        imageView {
                            id = R.id.iv_ctg_badge
                        }.lparams(width = dip(55), height = dip(55))
                    }

                    textView {
                        id = R.id.tv_ctg_name
                        gravity = Gravity.CENTER
                        maxWidth = dip(100)
                        maxLines = 2
                        ellipsize = TextUtils.TruncateAt.END
                        textColorResource = R.color.colorText
                    }
                }
            }
        }
    }
}