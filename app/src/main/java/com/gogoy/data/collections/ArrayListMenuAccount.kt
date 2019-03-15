package com.gogoy.data.collections

import com.gogoy.R
import com.gogoy.data.models.AccountMenuModel

class ArrayListMenuAccount {

    companion object {
        val list: ArrayList<AccountMenuModel> = arrayListOf(
            AccountMenuModel("1", "Transaksi", R.drawable.ic_transaction_primary_24dp),
            AccountMenuModel("2", "Favorit", R.drawable.ic_favorite_primary_24dp),
            AccountMenuModel("3", "Bantuan", R.drawable.ic_help_primary_24dp),
            AccountMenuModel("SET", "Pengaturan Akun", R.drawable.ic_settings_primary_24dp)
        )
    }
}