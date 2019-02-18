package com.gogoy.components.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

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
        super.onBackPressed()
        startActivity<MainActivity>()
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
        finish()
    }

    //set actionbar
    private fun setToolbar() {
        setActionBar(ui.toolbar)
        actionBar?.title = resources.getString(R.string.cart)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}