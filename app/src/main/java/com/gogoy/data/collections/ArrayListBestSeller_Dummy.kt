package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.ItemModel

class ArrayListBestSeller_Dummy {
    companion object {
        var list: ArrayList<ItemModel> = arrayListOf(
            ItemModel("pIj7w", "Setroberi", 7000, "Fruity", R.drawable.setroberi),
            ItemModel("aqgae", "Wortel", 9000, "Kang Jamal", R.drawable.wortel),
            ItemModel("F6bgc", "Jeruk Kuning", 11000, "Fruity", R.drawable.jeruk),
            ItemModel("5w5QD", "Pumpkin", 13000, "Pumpkindo", R.drawable.pumpkin)
        )
    }
}