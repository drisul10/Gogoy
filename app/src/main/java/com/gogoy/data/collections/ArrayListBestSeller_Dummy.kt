package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.ItemModel

class ArrayListBestSeller_Dummy {
    companion object {
        var list: ArrayList<ItemModel> = arrayListOf(
            ItemModel("1", "Setroberi", "7000", "Fruity", R.drawable.setroberi),
            ItemModel("2", "Wortel", "9000", "Kang Jamal", R.drawable.wortel),
            ItemModel("3", "Jeruk Kuning", "11000", "Fruity", R.drawable.jeruk),
            ItemModel("4", "Pumpkin", "13000", "Pumpkindo", R.drawable.pumpkin)
        )
    }
}