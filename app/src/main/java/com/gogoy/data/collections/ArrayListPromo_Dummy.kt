package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.ItemModel

class ArrayListPromo_Dummy {
    companion object {
        var list : ArrayList<ItemModel> = arrayListOf(
            ItemModel("1", "Gedang", "1000", "Sang Gedang", R.drawable.gedang),
            ItemModel("2", "Bawang", "3000", "PT Bawang", R.drawable.bawang),
            ItemModel("3", "Paprika", "5000", "PT Lombok", R.drawable.paprika)
        )
    }
}