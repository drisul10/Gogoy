package com.gogoy.data.models

import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("_id")
    var id: String,
    @SerializedName("fotobanner")
    var badge: String
)