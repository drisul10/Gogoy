package com.gogoy.components.items

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView

class ItemsActivity : AppCompatActivity() {

    private lateinit var itemsPresenter: ItemsPresenter
    val ui = ItemsUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //get intent
        val idCategory: String? = intent.getStringExtra("ID")
        val nameCategory: String? = intent.getStringExtra("NAME")

        //call toolbar
        setToolbar(nameCategory)

        //set default fragment
        val itemsFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as ItemsFragment? ?: ItemsFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        itemsPresenter = ItemsPresenter(itemsFragment)
    }

    //on press back
    override fun onBackPressed() {
        startActivity(intentFor<MainActivity>().clearTask().newTask())
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    //set actionbar
    private fun setToolbar(title: String?) {
        setActionBar(ui.toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.title = title

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}