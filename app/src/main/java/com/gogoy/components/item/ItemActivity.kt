package com.gogoy.components.item

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import com.gogoy.R
import com.gogoy.components.cart.CartActivity
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ItemActivity : AppCompatActivity() {

    private lateinit var itemPresenter: ItemPresenter
    private val ui = ItemUI()
    private lateinit var itemId: String
    private lateinit var itemName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        //get intent
        itemId = intent.getStringExtra(Constant.UP_ID)
        itemName = intent.getStringExtra(Constant.UP_ITEM_NAME)

        //call toolbar
        setToolbar(itemName)

        //create object fragment and pass data via bundle
        val bundle = Bundle()
        bundle.putString(Constant.UP_ID, itemId)

        val fragmentObj = ItemFragment.newInstance()
        fragmentObj.arguments = bundle

        //set default fragment
        val itemFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as ItemFragment? ?: fragmentObj.also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }

        itemPresenter = ItemPresenter(itemFragment, applicationContext)
    }

    //set actionbar
    private fun setToolbar(title: String?) {
        setSupportActionBar(ui.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = title

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (!(intent.getStringExtra(Constant.ACTIVITY_BEFORE).isNullOrEmpty())) {
            val activityBefore: String = intent.getStringExtra(Constant.ACTIVITY_BEFORE)

            when (activityBefore) {
                Constant.ACTIVITY_MAIN -> startActivity(intentFor<MainActivity>())
                Constant.ACTIVITY_CART -> startActivity(intentFor<MainActivity>().clearTask().newTask())
                Constant.ACTIVITY_CART_BACK -> startActivity(intentFor<CartActivity>().clearTask().newTask())
                Constant.ACTIVITY_ITEM -> super.onBackPressed()
                else -> startActivity(intentFor<MainActivity>().clearTask().newTask())
            }
        } else {
            startActivity(intentFor<MainActivity>().clearTask().newTask())
        }
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // inflate menu
        menuInflater.inflate(R.menu.menu_option_item, menu)

        //setting cart
        val menuCart: MenuItem = menu!!.findItem(R.id.menu_cart)
        @Suppress("DEPRECATION")
        MenuItemCompat.setActionView(menuCart, R.layout.layout_cart_count)
        @Suppress("DEPRECATION")
        val layoutCountItemCart = MenuItemCompat.getActionView(menuCart) as RelativeLayout
        val tvCountItemCart = layoutCountItemCart.findViewById<TextView>(R.id.tv_total_item_cart)

        val prefs = SharedPref(this)

        //set state tvCountItemCart
        tvCountItemCart.text = (prefs.cartGetItems().size).toString()
        tvCountItemCart.visible()

        if (tvCountItemCart.text == "0") tvCountItemCart.invisible()

        //when click icon cart
        layoutCountItemCart.onClick {
            startActivity<CartActivity>(
                Constant.ACTIVITY_BEFORE to Constant.ACTIVITY_ITEM,
                Constant.UP_ID to itemId,
                Constant.UP_ITEM_NAME to itemName
            )
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        return super.onCreateOptionsMenu(menu)
    }
}