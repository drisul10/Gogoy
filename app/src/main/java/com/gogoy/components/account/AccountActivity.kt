package com.gogoy.components.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView

class AccountActivity : AppCompatActivity() {

    private lateinit var accountPresenter: AccountPresenter
    val ui = AccountUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //call toolbar
        setToolbar()

        //set default fragment
        val accountFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as AccountFragment? ?: AccountFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        accountPresenter = AccountPresenter(accountFragment)
    }

    //on press back
    override fun onBackPressed() {
        startActivity(intentFor<MainActivity>().clearTask().newTask())
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    //set actionbar
    private fun setToolbar() {
        setActionBar(ui.toolbar)
        actionBar?.title = resources.getString(R.string.account)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}