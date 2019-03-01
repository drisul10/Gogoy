package com.gogoy.components.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.item.ItemActivity
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView

class CartActivity : AppCompatActivity() {

    private lateinit var cartPresenter: CartPresenter
    val ui = CartUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //call toolbar
        setToolbar()

        //set default fragment
        val cartFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as CartFragment? ?: CartFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        cartPresenter = CartPresenter(cartFragment)
    }

    //on press back
    override fun onBackPressed() {
        val activityOrigin: String = intent.getStringExtra("ACTIVITY_ORIGIN")

        when (activityOrigin) {
            "MAIN" -> startActivity(intentFor<MainActivity>("ACTIVITY_ORIGIN" to "CART").clearTask().newTask())
            "ITEM" -> {
                val itemId: String = intent.getStringExtra("ID")
                val itemName: String = intent.getStringExtra("NAME")
                val itemPrice: Int = intent.getIntExtra("PRICE", 0)
                val itemOwner: String = intent.getStringExtra("OWNER")
                val itemBadge: Int = intent.getIntExtra("BADGE", 0)

                startActivity(
                    intentFor<ItemActivity>(
                        "ACTIVITY_ORIGIN" to "CART",
                        "ID" to itemId,
                        "NAME" to itemName,
                        "PRICE" to itemPrice,
                        "OWNER" to itemOwner,
                        "BADGE" to itemBadge
                    ).clearTask().newTask()
                )
            }
        }

        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    //set actionbar
    private fun setToolbar() {
        setSupportActionBar(ui.toolbar)
        supportActionBar?.title = resources.getString(R.string.cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}