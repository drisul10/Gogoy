package com.gogoy.components.items

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.Constant
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView

class ItemsActivity : AppCompatActivity() {

    private lateinit var itemsPresenter: ItemsPresenter
    private var intentItemsTypeId: Int = 0
    private lateinit var intentTitle: String
    private val ui = ItemsUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)

        intentItemsTypeId = intent.getIntExtra(Constant.UP_TYPE_ID, 0)
        intentTitle = intent.getStringExtra(Constant.UP_TITLE)

        val bundle = Bundle()
        bundle.putInt(Constant.UP_TYPE_ID, intentItemsTypeId)

        val fragmentObj = ItemsFragment.instance()
        fragmentObj.arguments = bundle


        val itemsFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as ItemsFragment? ?: fragmentObj.also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        itemsPresenter = ItemsPresenter(itemsFragment, applicationContext)

        setToolbar(intentTitle)
    }

    override fun onBackPressed() {
        startActivity(intentFor<MainActivity>().clearTask().newTask())
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

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