package com.gogoy.components.account

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gogoy.R
import com.gogoy.components.account.settings.SettingsActivity
import com.gogoy.data.models.AccountMenuModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class AccountMenuAdapter(
    private val context: Context,
    private var list: ArrayList<AccountMenuModel> = arrayListOf()
) : RecyclerView.Adapter<AccountMenuAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AccountMenuAdapter.PartialUI().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.tvName.text = item.name
        holder.ivBadge.setImageResource(item.badge)

        holder.container.onClick {
            val activity = context as Activity

            when (item.id) {
                "SET" -> {
                    activity.startActivity<SettingsActivity>()
                }
            }

            activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.find(R.id.tv_menu_name)
        var ivBadge: ImageView = view.find(R.id.iv_menu_badge)
        var container: LinearLayout = view.find(R.id.container)
    }

    private class PartialUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                padding = dip(15)

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    id = R.id.container
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL

                    imageView {
                        id = R.id.iv_menu_badge
                    }.lparams(width = dip(35), height = dip(35)) {
                        marginEnd = dip(15)
                    }

                    textView {
                        id = R.id.tv_menu_name
                        textSize = 16f
                        textColorResource = R.color.colorText
                    }
                }
            }
        }
    }
}