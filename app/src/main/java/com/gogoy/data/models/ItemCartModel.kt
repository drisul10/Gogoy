package com.gogoy.data.models

data class ItemCartModel(
    var id: String,
    var name: String,
    var price: Int,
    var owner: String,
    var badge: Int,
    var totalPerItem: Int
)