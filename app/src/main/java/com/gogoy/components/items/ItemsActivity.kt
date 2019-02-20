package com.gogoy.components.items

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class ItemsActivity : AppCompatActivity() {

    private lateinit var itemsPresenter: ItemsPresenter
    val ui = ItemsUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //call toolbar
        setToolbar()

        //set default fragment
        val itemsFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as ItemsFragment? ?: ItemsFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        itemsPresenter = ItemsPresenter(itemsFragment)
    }

    //set actionbar
    private fun setToolbar() {
        setActionBar(ui.toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}