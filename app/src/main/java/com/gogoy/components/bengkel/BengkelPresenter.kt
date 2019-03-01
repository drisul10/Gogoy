package com.gogoy.components.bengkel

class BengkelPresenter(private val view: BengkelContract.View) : BengkelContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadMaps()
    }

    override fun loadMaps() {
        view.showMaps()
    }

    override fun loadSearch() {
        view.showSearch()
    }
}