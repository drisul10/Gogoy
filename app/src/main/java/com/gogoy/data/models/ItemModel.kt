package com.gogoy.data.models

import com.google.gson.annotations.SerializedName

data class ItemModel(
    @SerializedName("_id")
    var id: String,
    @SerializedName("nama_brg")
    var name: String,
    @SerializedName("harga")
    var price: Int,
    @SerializedName("category")
    var owner: String,
    @SerializedName("foto")
    var badge: String
)