package com.gogoy.components.base

interface BaseView<T> {
    var presenter: T
    fun onPresenterStart()
    fun progressBarHide(id: Any)
    fun progressBarShow(id: Any)
    fun snackbarChange(message: String, color: Int)
    fun snackbarDismissOn(delay: Long)
    fun snackbarInit()
    fun snackbarShow(message: String, color: Int)
    fun swipe()
}