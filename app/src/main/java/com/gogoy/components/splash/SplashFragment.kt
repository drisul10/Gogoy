package com.gogoy.components.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.components.auth.login.LoginActivity
import com.gogoy.utils.HandleMessage
import com.gogoy.utils.SharedPref
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.find

class SplashFragment : Fragment(), SplashContract.View, HandleMessage {

    override lateinit var presenter: SplashContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    val ui = SplashFragmentUI.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onPresenterStart() {
        this.handleRoute()
    }

    override fun handleRoute() {
        val sharedPref = SharedPref(requireContext())

        Handler().postDelayed({
            if (sharedPref.getTokenAuth() != null) {
                presenter.requestDataUser(sharedPref.getTokenAuth() as String)
            } else {
                activity!!.startActivity<LoginActivity>()
            }
//        }, 2000)
        }, 100)
    }

    override fun progressBarHide(id: Any) {
        //TODO
    }

    override fun progressBarShow(id: Any) {
        //TODO
    }

    override fun snackbarChange(message: String, color: Int) {
        super.change(message, color)
    }

    override fun snackbarDismissOn(delay: Long) {
        super.dismissOn(delay)
    }

    override fun snackbarInit() {
        rootLayout = find(R.id.rl_root)
        snackbar = Snackbar.make(rootLayout, R.string.loading, Snackbar.LENGTH_INDEFINITE)
    }

    override fun snackbarShow(message: String, color: Int) {
        super.show(message, color)
    }

    override fun swipe() {
        //TODO
    }

    companion object {
        fun instance() = SplashFragment()
    }
}