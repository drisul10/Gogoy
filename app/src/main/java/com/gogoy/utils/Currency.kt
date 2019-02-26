package com.gogoy.utils

import java.text.NumberFormat
import java.util.*

fun toRupiah(string: Int): String {
    val localeID = Locale("in", "ID")
    val formatRupiah = NumberFormat.getCurrencyInstance(localeID)

    return formatRupiah.format(string)
}