package com.gogoy.data.collections

import com.gogoy.data.models.SettingsMenuModel

class ArrayListMenuSettings {

    companion object {
        val list: ArrayList<SettingsMenuModel> = arrayListOf(
            SettingsMenuModel("EDIT_PROFILE", "Edit Profil"),
            SettingsMenuModel("LOGOUT", "Logout")
        )
    }
}