package com.gogoy.utils

import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gogoy.R
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.onRefresh

interface HandleSwipe {

    fun swipe(view: View?) {
        val swipeRefresh = view?.find<SwipeRefreshLayout>(R.id.swipe_refresh)

        swipeRefresh?.onRefresh {
            swipeRefresh.isRefreshing = false

            swipeHideView()
            swipeShowView()
            swipeRequest()
        }
    }

    fun swipeHideView()
    fun swipeRequest()
    fun swipeShowView()
}