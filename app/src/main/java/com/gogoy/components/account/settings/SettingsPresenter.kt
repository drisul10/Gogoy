package com.gogoy.components.account.settings

class SettingsPresenter(private val view: SettingsContract.View) : SettingsContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        loadItem()
    }

    override fun loadItem() {
        view.showItem()
    }
}