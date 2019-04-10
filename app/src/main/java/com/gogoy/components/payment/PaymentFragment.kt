package com.gogoy.components.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.gogoy.R
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.Constant
import com.gogoy.utils.HandleMessage
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.find

class PaymentFragment : Fragment(), PaymentContract.View, HandleMessage {

    private lateinit var itemAdapter: ItemAdapter
    override lateinit var presenter: PaymentContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar
    private var argIds: ArrayList<String>? = null
    val ui = PaymentFragmentUI.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        argIds = arguments?.getStringArrayList(Constant.UP_IDS)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onPresenterStart() {
        presenter.requestItem(argIds)
    }

    override fun progressBarHide(id: Any) {
        //TODO
    }

    override fun progressBarShow(id: Any) {
        //TODO
    }

    override fun showItem(data: MutableList<ItemPromoModel>) {
        itemAdapter = ItemAdapter(requireContext(), data)
        ui.rvItem.adapter = itemAdapter
        itemAdapter.notifyDataSetChanged()
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
        //TODO
    }

    companion object {
        fun instance() = PaymentFragment()
    }
}