package com.gogoy.components.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.utils.Constant
import org.jetbrains.anko.AnkoContext

class TabDescFragment : Fragment() {

    val ui = TabDescUI.instance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argDesc = arguments?.getString(Constant.UP_DESC)
        ui.tvDesc.text = argDesc
    }

    companion object {
        fun instance() = TabDescFragment()
    }
}