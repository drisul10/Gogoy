package com.gogoy.components.items

import androidx.fragment.app.FragmentActivity
import com.gogoy.components.base.BasePresenter
import com.gogoy.components.base.BaseView
import com.gogoy.data.models.ItemModel

interface ItemsContract {
    interface View : BaseView<Presenter> {
        fun hideItems()
        fun selectItems()
        fun showItems(data: Collection<ItemModel>)
    }

    interface Presenter : BasePresenter {
        fun getCodeRequestItems(): Int
        fun requestItems(v: android.view.View, activity: FragmentActivity, url: String)
    }
}