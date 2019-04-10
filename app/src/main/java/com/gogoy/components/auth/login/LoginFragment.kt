package com.gogoy.components.auth.login

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.components.auth.forgotPassword.sendCode.SendCodeActivity
import com.gogoy.components.auth.register.RegisterActivity
import com.gogoy.utils.HandleMessage
import com.gogoy.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.intentFor

class LoginFragment : Fragment(), LoginContract.View, HandleMessage {

    override lateinit var presenter: LoginContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    val ui = LoginFragmentUI.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onPresenterStart() {
        this.clickForgotPassword()
        this.clickLogin()
        this.clickRegister()
        this.snackbarInit()
    }

    override fun progressBarHide(id: Any) {
        //TODO
    }

    override fun clickForgotPassword() {
        ui.tvForgotPassword.onClick {
            activity?.startActivity(intentFor<SendCodeActivity>().clearTask())
            activity?.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

    override fun clickLogin() {
        ui.btnLogin.onClick {
            hideKeyboard()
            snackbarShow(getString(R.string.loading), Color.BLACK)
            presenter.requestLogin(view, requireActivity())
        }
    }

    override fun clickRegister() {
        ui.tvRegister.onClick {
            activity?.startActivity(intentFor<RegisterActivity>().clearTask())
            activity?.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
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
        fun instance() = LoginFragment()
    }
}