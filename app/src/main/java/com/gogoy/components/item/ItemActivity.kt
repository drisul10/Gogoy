package com.gogoy.components.item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class ItemActivity : AppCompatActivity() {

    private lateinit var itemPresenter: ItemPresenter
    val ui = ItemUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //call toolbar
        setToolbar()

        //set default fragment
        val itemFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as ItemFragment? ?: ItemFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        itemPresenter = ItemPresenter(itemFragment)
    }

    //set actionbar
    private fun setToolbar() {
        setActionBar(ui.toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.title = "Lombok Abang"

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}