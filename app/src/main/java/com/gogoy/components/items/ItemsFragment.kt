package com.gogoy.components.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.data.collections.ArrayListItem_Dummy
import org.jetbrains.anko.AnkoContext

class ItemsFragment : Fragment(), ItemsContract.View {
    override lateinit var presenter: ItemsContract.Presenter
    val ui = ItemsFragmentUI<Fragment>()
    private lateinit var itemAdapter: ItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()
    }

    override fun showItem() {
        //TODO: real data from API
        itemAdapter = ItemAdapter(ArrayListItem_Dummy.list)
        ui.rvItem.adapter = itemAdapter
    }

    companion object {
        fun newInstance() = ItemsFragment()
    }
}