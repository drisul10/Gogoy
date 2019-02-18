package com.gogoy.components.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.data.collections.ArrayListItem_Dummy
import org.jetbrains.anko.AnkoContext

class CartFragment : Fragment(), CartContract.View {

    override lateinit var presenter: CartContract.Presenter
    val ui = CartFragmentUI<Fragment>()
    private lateinit var cartItemAdapter: CartItemAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()
    }

    override fun showItem() {
        cartItemAdapter = CartItemAdapter(ArrayListItem_Dummy.list)
        ui.rvCartItem.adapter = cartItemAdapter
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}