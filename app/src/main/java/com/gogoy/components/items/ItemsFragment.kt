package com.gogoy.components.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.data.models.ItemModel
import com.gogoy.utils.*
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.find

class ItemsFragment : Fragment(), ItemsContract.View, HandleMessage, HandleSwipe {

    private var argItemsTypeId: Int = 0
    private lateinit var itemAdapter: ItemAdapter
    private var items: MutableList<ItemModel> = mutableListOf()
    override lateinit var presenter: ItemsContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    val ui = ItemsFragmentUI<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        argItemsTypeId = arguments!!.getInt(Constant.UP_TYPE_ID)
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onPresenterStart() {
        hideItems()
        swipe()
        selectItems()
    }

    override fun hideItems() {
        ui.rvItems.gone()
    }

    override fun progressBarHide(id: Any) {
        when (id) {
            presenter.getCodeRequestItems() -> ui.pbItems.gone()
        }
    }

    override fun selectItems() {
        val url = setUrl(
            argItemsTypeId,
            Constant.ID_ITEM_ALL_SAYUR to Constant.URL_ITEM_ALL_SAYUR,
            Constant.ID_ITEM_ALL_KOMODITI to Constant.URL_ITEM_ALL_KOMODITI,
            Constant.ID_ITEM_ALL_PROMO_KOMODITI to Constant.URL_ITEM_ALL_PROMO_KOMODITI,
            Constant.ID_ITEM_ALL_PROMO_SAYUR to Constant.URL_ITEM_ALL_PROMO_SAYUR
        )

        presenter.requestItems(view as View, requireActivity(), url)
    }

    override fun showItems(data: Collection<ItemModel>) {
        ui.rvItems.visible()
        items.clear()
        items.addAll(data)
        itemAdapter = ItemAdapter(requireContext(), items)
        ui.rvItems.adapter = itemAdapter
    }

    override fun progressBarShow(id: Any) {
        when (id) {
            presenter.getCodeRequestItems() -> ui.pbItems.visible()
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
        hideItems()
    }

    override fun swipeRequest() {
        val customUrl = setUrl(
            argItemsTypeId,
            Constant.ID_ITEM_ALL_PROMO_KOMODITI to Constant.URL_ITEM_ALL_PROMO_KOMODITI,
            Constant.ID_ITEM_ALL_PROMO_SAYUR to Constant.URL_ITEM_ALL_PROMO_SAYUR
        )

        presenter.requestItems(view as View, requireActivity(), customUrl)
    }

    override fun swipeShowView() {
        progressBarShow(R.id.pb_items)
    }

    companion object {
        fun instance() = ItemsFragment()
    }
}