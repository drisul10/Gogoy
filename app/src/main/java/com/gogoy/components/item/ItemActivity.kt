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
import com.gogoy.utils.Prefs
import com.gogoy.utils.invisible
import com.gogoy.utils.replaceFragmentInActivity
import com.gogoy.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class ItemActivity : AppCompatActivity() {

    private lateinit var itemPresenter: ItemPresenter
    private val ui = ItemUI()
    private lateinit var itemId: String
    private lateinit var itemName: String
    private lateinit var itemOwner: String
    private var itemPrice: Int = 0
    private var itemBadge: Int = R.drawable.default_image

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //get intent
        itemId = intent.getStringExtra("ID")
        itemName = intent.getStringExtra("NAME")
        itemOwner = intent.getStringExtra("OWNER")
        itemPrice = intent.getIntExtra("PRICE", 0)
        itemBadge = intent.getIntExtra("BADGE", R.drawable.default_image)

        //call toolbar
        setToolbar(itemName)

        //create object fragment and pass data via bundle
        val bundle = Bundle()
        bundle.putString("ID", itemId)
        bundle.putString("NAME", itemName)
        bundle.putString("OWNER", itemOwner)
        bundle.putInt("PRICE", itemPrice)
        bundle.putInt("BADGE", itemBadge)

        val fragmentObj = ItemFragment.newInstance()
        fragmentObj.arguments = bundle

        //set default fragment
        val itemFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as ItemFragment? ?: fragmentObj.also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }

        itemPresenter = ItemPresenter(itemFragment)
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
        val activityOrigin: String = intent.getStringExtra("ACTIVITY_ORIGIN")

        when (activityOrigin) {
            "MAIN" -> startActivity(intentFor<MainActivity>().clearTask().newTask())
            "CART" -> startActivity(intentFor<MainActivity>().clearTask().newTask())
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

        val prefs = Prefs(this)

        //set state tvCountItemCart
        tvCountItemCart.text = (prefs.getPref().size).toString()
        tvCountItemCart.visible()

        if (tvCountItemCart.text == "0") tvCountItemCart.invisible()

        //when click icon cart
        layoutCountItemCart.onClick {
            startActivity<CartActivity>(
                "ACTIVITY_ORIGIN" to "ITEM",
                "ID" to itemId,
                "NAME" to itemName,
                "PRICE" to itemPrice,
                "OWNER" to itemOwner,
                "BADGE" to itemBadge
            )
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        return super.onCreateOptionsMenu(menu)
    }
}