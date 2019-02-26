package com.gogoy.components.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.gogoy.R
import com.gogoy.components.account.AccountActivity
import com.gogoy.components.cart.CartActivity
import com.gogoy.utils.Prefs
import com.gogoy.utils.invisible
import com.gogoy.utils.replaceFragmentInActivity
import com.gogoy.utils.visible
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        MainUI().setContentView(this)

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

        val prefs = Prefs(this)

        //set state tvCountItemCart
        tvCountItemCart.text = (prefs.getPref().size).toString()
        tvCountItemCart.visible()

        if (tvCountItemCart.text == "0") tvCountItemCart.invisible()

        //when click icon cart
        layoutCountItemCart.onClick {
            startActivity<CartActivity>()
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
}