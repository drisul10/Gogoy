package com.gogoy.components.item

class ItemPresenter(private val view: ItemContract.View) : ItemContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadItemRelated()
    }

    override fun loadItemRelated() {
        view.showItemRelated()
    }
}