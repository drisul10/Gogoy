package com.gogoy.components.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.data.collections.ArrayListMenuAccount
import com.gogoy.utils.HandleMessage
import com.gogoy.utils.SharedPref
import com.gogoy.utils.decodeImageBase64ToBitmap
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.find

class AccountFragment : Fragment(), AccountContract.View, HandleMessage {

    val ui = AccountFragmentUI<Fragment>()
    private lateinit var accountMenuAdapter: AccountMenuAdapter
    override lateinit var presenter: AccountContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    private lateinit var pref: SharedPref

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()
    }

    override fun onPresenterStart() {
        //TODO
    }

    override fun progressBarHide(id: Any) {
        //TODO
    }

    override fun showMenu() {
        accountMenuAdapter = AccountMenuAdapter(requireContext(), ArrayListMenuAccount.list)
        ui.rvMenuAccount.adapter = accountMenuAdapter
    }

    override fun progressBarShow(id: Any) {
        //TODO
    }

    override fun showUserProfile() {
        pref = SharedPref(requireContext())
        ui.tvUserDisplayName.text = pref.userDisplayName

        if (pref.userBadge.isNullOrEmpty()) {
            ui.ivBadge.setImageResource(R.drawable.default_image)
        } else {
            ui.ivBadge.setImageBitmap(decodeImageBase64ToBitmap(pref.userBadge))
        }
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
        fun newInstance() = AccountFragment()
    }
}