package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.CategoryModel

class ArrayListCategory {
    companion object {
        val list: ArrayList<CategoryModel> = arrayListOf(
            CategoryModel("1", "Promo", R.drawable.gogoy),
            CategoryModel("2", "Sayuran", R.drawable.gogoy),
            CategoryModel("3", "Buah", R.drawable.gogoy),
            CategoryModel("4", "Lainnya", R.drawable.gogoy)
        )
    }
}