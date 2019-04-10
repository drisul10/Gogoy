package com.gogoy.components.auth.forgotPassword.resetPassword

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.utils.Constant
import com.gogoy.utils.HandleMessage
import com.gogoy.utils.hideKeyboard
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.find

class ResetPasswordFragment : Fragment(), ResetPasswordContract.View, HandleMessage {

    private var argToken: String? = null
    override lateinit var presenter: ResetPasswordContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    val ui = ResetPasswordFragmentUI.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        argToken = arguments!!.getString(Constant.UP_TOKEN)
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onPresenterStart() {
        this.clickResetPassword()
        this.snackbarInit()
    }

    override fun clickResetPassword() {
        ui.btnProcess.onClick {
            hideKeyboard()
            snackbarShow(getString(R.string.loading), Color.BLACK)
            presenter.resetResetPassword(view as View, requireActivity(), argToken as String)
        }
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
        fun instance() = ResetPasswordFragment()
    }
}