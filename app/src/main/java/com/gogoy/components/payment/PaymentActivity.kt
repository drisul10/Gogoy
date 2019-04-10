package com.gogoy.components.payment

import android.os.Bundle
import com.gogoy.R
import com.gogoy.components.base.BaseActivity
import com.gogoy.components.cart.CartActivity
import com.gogoy.components.main.MainActivity
import com.gogoy.utils.Constant
import com.gogoy.utils.replaceFragmentInActivity
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.setContentView

class PaymentActivity : BaseActivity() {

    private lateinit var paymentPresenter: PaymentPresenter
    val ui = PaymentUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui.setContentView(this)

        setToolbar()

        val intentIds = intent.getStringArrayListExtra(Constant.UP_IDS)

        //create object fragment and pass data via bundle
        val bundle = Bundle()
        bundle.putStringArrayList(Constant.UP_IDS, intentIds)

        val fragmentObj = PaymentFragment.instance()
        fragmentObj.arguments = bundle

        val paymentFragment = supportFragmentManager
            .findFragmentById(R.id.fl_main_content) as PaymentFragment? ?: fragmentObj.also {
            replaceFragmentInActivity(it, R.id.fl_main_content)
        }
        paymentPresenter = PaymentPresenter(paymentFragment, applicationContext)
    }

    override fun onBackPressed() {
        if (!(intent.getStringExtra(Constant.ACTIVITY_BEFORE).isNullOrEmpty())) {
            val activityBefore: String = intent.getStringExtra(Constant.ACTIVITY_BEFORE)

            when (activityBefore) {
                Constant.ACTIVITY_CART -> startActivity(intentFor<CartActivity>().clearTask().newTask())
            }
        } else {
            startActivity(intentFor<MainActivity>().clearTask().newTask())
        }

        overridePendingTransition(R.anim.left_in, R.anim.right_out)
    }

    override fun onNoInternet() {
        //TODO
    }

    private fun setToolbar() {
        setSupportActionBar(ui.toolbar)
        supportActionBar?.title = resources.getString(R.string.set_payment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        ui.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}