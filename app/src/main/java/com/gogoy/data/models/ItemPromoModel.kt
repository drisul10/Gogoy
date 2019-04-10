package com.gogoy.data.models

import com.gogoy.utils.Constant
import com.google.gson.annotations.SerializedName

data class ItemPromoModel(
    @SerializedName("_id")
    var id: String,
    @SerializedName("nama_brg")
    var name: String,
    @SerializedName("harga")
    var price: Int,
    @SerializedName("dimensi")
    var dimension: Int,
    @SerializedName("satuan")
    var unit: String,
    @SerializedName("foto")
    var badge: String,
    @SerializedName("category")
    var category: String,
    @SerializedName("id_user")
    var owner_id: String,
    @SerializedName("updated_at")
    var updated_at: String = Constant.NULL,
    @SerializedName("created_at")
    var created_at: String = Constant.NULL,
    @SerializedName("deskripsi")
    var desc: String = Constant.NULL,
    @SerializedName("info")
    var info: String = Constant.NULL,
    @SerializedName("status")
    var status: String = Constant.NULL,
    @SerializedName("hargadiskon")
    var discount_price: Int = 0
)