package com.gogoy.components.account.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.components.account.AccountActivity
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView

class SettingsActivity : AppCompatActivity() {

    val ui = SettingsUI()
    private lateinit var settingsPresenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set UI
        ui.setContentView(this)

        //call toolbar
        setToolbar()

        //set default fragment
        val settingsFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as SettingsFragment? ?: SettingsFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        settingsPresenter = SettingsPresenter(settingsFragment)
    }

    //set actionbar
    private fun setToolbar() {
        setActionBar(ui.toolbar)
        actionBar?.title = resources.getString(R.string.account_settings)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    //on press back
    override fun onBackPressed() {
        startActivity(intentFor<AccountActivity>().clearTask().newTask())
        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }
}