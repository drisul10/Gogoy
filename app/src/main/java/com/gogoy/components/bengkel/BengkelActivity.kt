package com.gogoy.components.bengkel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView

class BengkelActivity : AppCompatActivity() {

    private lateinit var bengkelPresenter: BengkelPresenter
    val ui = BengkelUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //get intent
        val nameCategory: String? = intent.getStringExtra("NAME")

        //call toolbar
        setToolbar(nameCategory)

        //set default fragment
        val bengkelFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as BengkelFragment? ?: BengkelFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        bengkelPresenter = BengkelPresenter(bengkelFragment)
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