package com.gogoy.components.account.settings.editprofile

class EditProfilePresenter(private val view: EditProfileContract.View) : EditProfileContract.Presenter {
    init {
        view.presenter = this
    }

    override fun start() {
        loadForm()
    }

    override fun loadForm() {
        view.showForm()
    }
}