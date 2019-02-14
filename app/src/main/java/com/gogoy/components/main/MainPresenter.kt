package com.gogoy.components.main

class MainPresenter(private var view : MainContract.View) : MainContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadBanner()
        loadCategory()
        loadPromo()
        loadBestSeller()
    }

    override fun loadBanner() {
        view.showBanner()
    }

    override fun loadCategory() {
        view.showCategory()
    }

    override fun loadPromo() {
        view.showPromo()
    }

    override fun loadBestSeller() {
        view.showBestSeller()
    }
}