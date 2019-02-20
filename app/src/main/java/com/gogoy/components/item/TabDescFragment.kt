package com.gogoy.components.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.jetbrains.anko.AnkoContext

class TabDescFragment : Fragment() {

    val ui = TabDescUI<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))
}