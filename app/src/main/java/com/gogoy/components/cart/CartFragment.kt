package com.gogoy.components.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.*
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.find

class CartFragment : Fragment(), CartContract.View, HandleMessage, HandleSwipe {

    private lateinit var cartAdapter: CartAdapter
    override lateinit var presenter: CartContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    val ui = CartFragmentUI<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()
    }

    override fun onPresenterStart() {
        presenter.requestItem()
        this.swipe()
        this.hideUI(R.id.rv_cart_item)
        this.setViewState()
        this.snackbarInit()
    }

    override fun hideUI(id: Any) {
        when (id) {
            R.id.rv_cart_item -> ui.rvCartItem.gone()
        }
    }

    override fun progressBarHide(id: Any) {
        when (id) {
            R.id.pb_items_cart -> ui.pbItemsCart.gone()
        }
    }

    override fun progressBarShow(id: Any) {
        when (id) {
            R.id.pb_items_cart -> ui.pbItemsCart.visible()
        }
    }

    override fun setViewState() {
        if (SharedPref(requireContext()).cartGetItems().size == 0) {
            ui.pbItemsCart.gone()
        } else {
        }
    }

    override fun showItem(data: MutableList<ItemPromoModel>) {
        cartAdapter = CartAdapter(requireContext(), data)
        ui.rvCartItem.adapter = cartAdapter
        cartAdapter.notifyDataSetChanged()
    }

    override fun showUI(id: Any) {
        when (id) {
            R.id.rv_cart_item -> ui.rvCartItem.visible()
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
        super.swipe(view)
    }

    override fun swipeHideView() {
        hideUI(R.id.rv_cart_item)
    }

    override fun swipeRequest() {
        presenter.requestItem(true)
    }

    override fun swipeShowView() {
        progressBarShow(R.id.pb_items_cart)
    }

    companion object {
        fun newInstance() = CartFragment()
    }
}