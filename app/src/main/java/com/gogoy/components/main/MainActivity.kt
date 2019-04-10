package com.gogoy.components.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuItemCompat
import com.gogoy.R
import com.gogoy.components.account.AccountActivity
import com.gogoy.components.cart.CartActivity
import com.gogoy.utils.SharedPref
import com.gogoy.utils.invisible
import com.gogoy.utils.replaceFragmentInActivity
import com.gogoy.utils.visible
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainPresenter: MainPresenter
    private val ui = MainUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //set default fragment
        val mainFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as MainFragment? ?: MainFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        mainPresenter = MainPresenter(mainFragment)
    }

    @Suppress("DEPRECATION")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate menu
        menuInflater.inflate(R.menu.menu_option_main, menu)

        //setting cart
        val menuCart: MenuItem = menu!!.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(menuCart, R.layout.layout_cart_count)
        val layoutCountItemCart = MenuItemCompat.getActionView(menuCart) as RelativeLayout
        val tvCountItemCart = layoutCountItemCart.findViewById<TextView>(R.id.tv_total_item_cart)

        val prefs = SharedPref(this)

        //set state tvCountItemCart
        tvCountItemCart.text = (prefs.cartGetItems().size).toString()
        tvCountItemCart.visible()

        if (tvCountItemCart.text == "0") tvCountItemCart.invisible()

        //when click icon cart
        layoutCountItemCart.onClick {
            startActivity<CartActivity>("ACTIVITY_ORIGIN" to "MAIN")
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.menu_account -> {
                startActivity<AccountActivity>()
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
                true
            }

            else -> false
        }
    }

    //on press back
    override fun onBackPressed() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(R.string.exit)
        alertDialog.setMessage(R.string.exit_message)
        alertDialog.setPositiveButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
        alertDialog.setNegativeButton(R.string.exit) { _, _ ->
            ActivityCompat.finishAffinity(this)
        }

        //create and show dialog
        val dialog = alertDialog.create()
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        setAccessLocation()
        setAccessGPS()
    }

    private fun setAccessLocation() {
        //access location
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MainActivity.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
    }

    private fun setAccessGPS() {
        val service = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val alertDialog = AlertDialog.Builder(this)

        if (!enabled) {
            alertDialog.setTitle(R.string.gps_disabled)

            alertDialog.setMessage(R.string.gps_enabled_message)
            alertDialog.setNegativeButton(R.string.activate) { _, _ ->

                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivityForResult(intent, 0)
            }

            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                //create and show dialog
                val dialog = alertDialog.create()
                dialog.show()
                dialog.setCancelable(false)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(ContextCompat.getColor(this, R.color.colorText))
            }
        } else {
            val dialog = alertDialog.create()
            dialog.dismiss()
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}