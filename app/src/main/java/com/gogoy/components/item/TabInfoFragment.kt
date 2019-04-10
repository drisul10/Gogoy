package com.gogoy.components.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.utils.Constant
import org.jetbrains.anko.AnkoContext

class TabInfoFragment : Fragment() {

    private lateinit var argInfo: String
    val ui = TabInfoUI.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        argInfo = arguments?.getString(Constant.UP_INFO) as String
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ui.tvInfo.text = argInfo
    }

    companion object {
        fun instance() = TabInfoFragment()
    }
}