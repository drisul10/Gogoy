package com.gogoy.components.account.settings.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gogoy.R
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.setContentView

class EditProfileActivity : AppCompatActivity() {

    val ui = EditProfileUI.instance()
    private lateinit var editProfilePresenter: EditProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ui.setContentView(this)

        //set default fragment
        val editProfileFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as EditProfileFragment? ?: EditProfileFragment.newInstance().also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }

        editProfilePresenter = EditProfilePresenter(editProfileFragment)

        setToolbar()
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
}