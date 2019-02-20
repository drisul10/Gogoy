package com.gogoy.components.items

class ItemsPresenter(private val view: ItemsContract.View) : ItemsContract.Presenter {

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