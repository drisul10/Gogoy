package com.gogoy.components.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.account.AccountActivity
import com.gogoy.components.cart.CartActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Appbar
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate menu
        menuInflater.inflate(R.menu.menu_option_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.menu_cart -> {
                startActivity<CartActivity>()
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
                true
            }

            R.id.menu_account -> {
                startActivity<AccountActivity>()
                overridePendingTransition(R.anim.right_in, R.anim.left_out)
                true
            }

            else -> false
        }
    }
}