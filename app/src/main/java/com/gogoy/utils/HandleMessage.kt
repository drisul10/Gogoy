package com.gogoy.utils

import android.os.Handler
import android.widget.RelativeLayout
import com.google.android.material.snackbar.Snackbar

interface HandleMessage {
    var rootLayout: RelativeLayout
    var snackbar: Snackbar

    fun change(message: String, color: Int) {
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(color)
        snackbar.setText(message)
    }

    fun dismissOn(delayMiliSecond: Long) {
        Handler().postDelayed({
            snackbar.dismiss()
        }, delayMiliSecond)
    }

    fun show(message: String, color: Int) {
        snackbar.setText(message)
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(color)
        snackbar.show()
    }
}