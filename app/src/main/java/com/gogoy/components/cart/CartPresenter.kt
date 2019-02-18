package com.gogoy.components.cart

class CartPresenter(private val view: CartContract.View) : CartContract.Presenter {

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