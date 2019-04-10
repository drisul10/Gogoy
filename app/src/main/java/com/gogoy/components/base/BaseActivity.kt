package com.gogoy.components.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.utils.Constant
import com.gogoy.utils.HandleNoInternet
import com.gogoy.utils.NetworkUtil
import io.sentry.Sentry

abstract class BaseActivity : AppCompatActivity(), HandleNoInternet {

    private val broadcastReceiverConnectionChanged = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (!NetworkUtil.isNetworkAvailable(context)) {
                println("no internet")
            } else {
                onNoInternet()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Sentry.init(Constant.SENTRY_DSN)
    }

    override fun onPause() {
        unregisterReceiver(broadcastReceiverConnectionChanged)
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        registerBroadcastReceiver()
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(broadcastReceiverConnectionChanged, intentFilter)
    }
}