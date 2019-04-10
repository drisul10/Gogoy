package com.gogoy.components.account

class AccountPresenter(private val view: AccountContract.View) : AccountContract.Presenter {

    init {
        view.presenter = this
    }

    override fun start() {
        loadMenu()
        loadUserProfile()
    }

    override fun loadMenu() {
        view.showMenu()
    }

    override fun loadUserProfile() {
        view.showUserProfile()
    }
}