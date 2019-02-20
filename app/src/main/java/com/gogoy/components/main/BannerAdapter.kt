package com.gogoy.components.main

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.data.models.BannerModel
import org.jetbrains.anko.*

class BannerAdapter(
    private var list: ArrayList<BannerModel> = arrayListOf()
) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.ivBadge.setImageResource(item.badge)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivBadge: ImageView = view.find(R.id.iv_banner_badge)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                orientation = LinearLayout.VERTICAL

                //container
                linearLayout {
                    lparams(width = matchParent, height = dip(150))

                    orientation = LinearLayout.VERTICAL

                    //image banner
                    imageView {
                        id = R.id.iv_banner_badge
                        scaleType = ImageView.ScaleType.CENTER_CROP
                    }.lparams(width = matchParent, height = matchParent) {
                        gravity = Gravity.CENTER_HORIZONTAL
                    }
                }
            }
        }
    }
}