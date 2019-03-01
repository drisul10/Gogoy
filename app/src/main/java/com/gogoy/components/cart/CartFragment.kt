package com.gogoy.components.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.utils.Prefs
import com.gogoy.utils.invisible
import com.gogoy.utils.toRupiah
import com.gogoy.utils.visible
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
        val prefs = Prefs(requireContext())

        cartItemAdapter = CartItemAdapter(requireContext(), prefs.getPref()) { totalBill ->
            ui.tvTotalBill.text = toRupiah(totalBill)
        }

        if (prefs.getPref().size == 0) {
            ui.llCheckOut.invisible()
        } else {
            ui.llCheckOut.visible()
        }

        ui.rvCartItem.adapter = cartItemAdapter
        cartItemAdapter.notifyDataSetChanged()
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}