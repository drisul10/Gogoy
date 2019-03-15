package com.gogoy.components.account.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.data.collections.ArrayListMenuSettings
import org.jetbrains.anko.AnkoContext

class SettingsFragment : Fragment(), SettingsContract.View {

    override lateinit var presenter: SettingsContract.Presenter
    val ui = SettingsFragmentUI<Fragment>()
    private lateinit var settingsMenuAdapter: SettingsMenuAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.start()
    }

    override fun showItem() {
        settingsMenuAdapter = SettingsMenuAdapter(requireContext(), ArrayListMenuSettings.list)
        ui.rvMenuSettings.adapter = settingsMenuAdapter
    }

    companion object {
        fun newInstance() = SettingsFragment()
    }
}