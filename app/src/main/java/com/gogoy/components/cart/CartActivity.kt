package com.gogoy.components.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.item.ItemActivity
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.Constant
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
        ui.setContentView(this)

        //call toolbar
        setToolbar()

        //set default fragment
        val cartFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as CartFragment? ?: CartFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        cartPresenter = CartPresenter(cartFragment, applicationContext)
    }

    //on press back
    override fun onBackPressed() {
        if (!(intent.getStringExtra(Constant.ACTIVITY_BEFORE).isNullOrEmpty())) {
            val activityBefore: String = intent.getStringExtra(Constant.ACTIVITY_BEFORE)

            when (activityBefore) {
                Constant.ACTIVITY_MAIN -> startActivity(intentFor<MainActivity>().clearTask().newTask())

                Constant.ACTIVITY_ITEM -> {
                    val itemId: String = intent.getStringExtra(Constant.UP_ID)
                    val itemName: String = intent.getStringExtra(Constant.UP_ITEM_NAME)

                    startActivity(
                        intentFor<ItemActivity>(
                            Constant.UP_ID to itemId,
                            Constant.UP_ITEM_NAME to itemName
                        ).clearTask().newTask()
                    )
                }
            }
        } else {
            startActivity(intentFor<MainActivity>().clearTask().newTask())
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