package com.gogoy.components.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.data.collections.ArrayListMenuAccount
import com.gogoy.utils.Prefs
import org.jetbrains.anko.AnkoContext

class AccountFragment : Fragment(), AccountContract.View {

    override lateinit var presenter: AccountContract.Presenter
    val ui = AccountFragmentUI<Fragment>()
    private lateinit var accountMenuAdapter: AccountMenuAdapter
    private lateinit var pref: Prefs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()

        pref = Prefs(requireContext())
        ui.tvUserDisplayName.text = pref.userDisplayName
    }

    override fun showMenu() {
        accountMenuAdapter = AccountMenuAdapter(requireContext(), ArrayListMenuAccount.list)
        ui.rvMenuAccount.adapter = accountMenuAdapter
    }

    companion object {
        fun newInstance() = AccountFragment()
    }
}