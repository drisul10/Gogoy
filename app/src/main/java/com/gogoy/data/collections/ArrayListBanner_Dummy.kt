package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.BannerModel

class ArrayListBanner_Dummy {
    companion object {
        var list: ArrayList<BannerModel> = arrayListOf(
            BannerModel("1", R.drawable.lombok_abang),
            BannerModel("2", R.drawable.wortel)
        )
    }
}