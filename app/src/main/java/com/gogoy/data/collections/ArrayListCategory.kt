package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.CategoryModel

class ArrayListCategory {
    companion object {
        val list: ArrayList<CategoryModel> = arrayListOf(
            CategoryModel("1", "Gondes Danguran", R.drawable.bumdes),
            CategoryModel("2", "Gondes Ngering", R.drawable.bumdes),
            CategoryModel("3", "Gondes Jatipurwa", R.drawable.bumdes)
        )
    }
}