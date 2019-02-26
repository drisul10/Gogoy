package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.ItemModel

class ArrayListItem_Dummy {
    companion object {
        var list: ArrayList<ItemModel> = arrayListOf(
            ItemModel("bSoR2", "Gedang", 1000, "Sang Gedang", R.drawable.gedang),
            ItemModel("AG5ED", "Bawang", 3000, "PT Bawang", R.drawable.bawang),
            ItemModel("msmFB", "Paprika", 5000, "PT Lombok", R.drawable.paprika)
        )
    }
}