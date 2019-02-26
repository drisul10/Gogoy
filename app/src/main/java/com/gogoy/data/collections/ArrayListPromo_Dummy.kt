package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.ItemCartModel

class ArrayListPromo_Dummy {
    companion object {
        var list: ArrayList<ItemCartModel> = arrayListOf(
            ItemCartModel("cuFiy", "Gedang", 1000, "Sang Gedang", R.drawable.gedang, 0),
            ItemCartModel("5KIBp", "Bawang", 3000, "PT Bawang", R.drawable.bawang, 0),
            ItemCartModel("b6SDW", "Paprika", 5000, "PT Lombok", R.drawable.paprika, 0)
        )
    }
}