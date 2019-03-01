package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.CategoryModel

class ArrayListCategory {
    companion object {
        val list: ArrayList<CategoryModel> = arrayListOf(
            CategoryModel("1", "Kang Sayur", R.drawable.gogoy),
            CategoryModel("2", "Kang Sembako", R.drawable.gogoy),
            CategoryModel("3", "Kang Bengkel", R.drawable.gogoy)
        )
    }
}