package com.gogoy.components.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.gogoy.R
import com.gogoy.components.items.ItemsActivity
import com.gogoy.data.collections.ArrayListCategory
import com.gogoy.data.models.BannerModel
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.Constant
import com.gogoy.utils.HandleMessage
import com.gogoy.utils.gone
import com.gogoy.utils.visible
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.onRefresh

class MainFragment : Fragment(), MainContract.View, HandleMessage {

    override lateinit var presenter: MainContract.Presenter
    private val ui = MainFragmentUI<Fragment>()
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var promoKomoditiAdapter: PromoAdapter
    private lateinit var promoSayurAdapter: PromoAdapter
    private lateinit var bestSellerAdapter: BestSellerAdapter
    private var itemsBanner: MutableList<BannerModel> = mutableListOf()
    private var itemsPromoKomoditi: MutableList<ItemPromoModel> = mutableListOf()
    private var itemsPromoSayur: MutableList<ItemPromoModel> = mutableListOf()
    override lateinit var rootLayout: RelativeLayout
    override lateinit var snackbar: Snackbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun hideBanner() {
        ui.rvBanner.gone()
    }

    override fun hidePromoKomoditi() {
        ui.rvPromoKomoditi.gone()
    }

    override fun hidePromoSayur() {
        ui.rvPromoSayur.gone()
    }

    override fun onClickViewAllPromoById() {
        ui.tvViewAllPromoKomoditi.onClick {
            activity!!.startActivity(
                activity!!.intentFor<ItemsActivity>(
                    Constant.UP_TYPE_ID to Constant.ID_ITEM_ALL_PROMO_KOMODITI,
                    Constant.UP_TITLE to getString(R.string.all_promo_komoditi)
                )
            )
            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        ui.tvViewAllPromoSayur.onClick {
            activity!!.startActivity(
                activity!!.intentFor<ItemsActivity>(
                    Constant.UP_TYPE_ID to Constant.ID_ITEM_ALL_PROMO_SAYUR,
                    Constant.UP_TITLE to getString(R.string.all_promo_sayur)
                ).newTask().clearTask()
            )
            activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

    override fun onPresenterStart() {
        presenter.loadBanner(activity!!.applicationContext, requireActivity())
        presenter.loadPromoKomoditi(activity!!.applicationContext, requireActivity())
        presenter.loadPromoSayur(activity!!.applicationContext, requireActivity())
        this.snackbarInit()
    }

    override fun onSwipeRefresh() {
        val swipeRefresh = activity!!.find<SwipeRefreshLayout>(R.id.swipe_refresh)

        swipeRefresh.onRefresh {
            hideBanner()
            hidePromoKomoditi()
            hidePromoSayur()
            progressBarShow(R.id.pb_banner)
            progressBarShow(R.id.pb_promo_komoditi)
            progressBarShow(R.id.pb_promo_sayur)
            presenter.loadBanner(activity!!.applicationContext, requireActivity())
            presenter.loadPromoKomoditi(activity!!.applicationContext, requireActivity())
            presenter.loadPromoSayur(activity!!.applicationContext, requireActivity())

            swipeRefresh.isRefreshing = false
        }
    }

    override fun progressBarHide(id: Any) {
        when (id) {
            R.id.pb_banner -> ui.pbBanner.gone()
            R.id.pb_promo_komoditi -> ui.pbPromoKomoditi.gone()
            R.id.pb_promo_sayur -> ui.pbPromoSayur.gone()
        }
    }

    override fun progressBarShow(id: Any) {
        when (id) {
            R.id.pb_banner -> ui.pbBanner.visible()
            R.id.pb_promo_komoditi -> ui.pbPromoKomoditi.visible()
            R.id.pb_promo_sayur -> ui.pbPromoSayur.visible()
        }
    }

    override fun showBanner(data: Collection<BannerModel>) {
        ui.rvBanner.visible()
        itemsBanner.clear()
        itemsBanner.addAll(data)
        bannerAdapter = BannerAdapter(itemsBanner)
        ui.rvBanner.adapter = bannerAdapter
    }

    override fun showCategory() {
        categoryAdapter = CategoryAdapter(requireContext(), ArrayListCategory.list)
        ui.rvCategory.adapter = categoryAdapter
    }

    override fun showPromoKomoditi(data: Collection<ItemPromoModel>) {
        ui.rvPromoKomoditi.visible()
        itemsPromoKomoditi.clear()
        itemsPromoKomoditi.addAll(data)

        promoKomoditiAdapter = PromoAdapter(requireContext(), itemsPromoKomoditi) {
            //update state menu cart in main activity
            activity!!.invalidateOptionsMenu()
        }

        ui.rvPromoKomoditi.adapter = promoKomoditiAdapter
    }

    override fun showPromoSayur(data: Collection<ItemPromoModel>) {
        ui.rvPromoSayur.visible()
        itemsPromoSayur.clear()
        itemsPromoSayur.addAll(data)

        promoSayurAdapter = PromoAdapter(requireContext(), itemsPromoSayur) {
            activity!!.invalidateOptionsMenu()
        }

        ui.rvPromoSayur.adapter = promoSayurAdapter
    }

    override fun showBestSeller() {
        //TODO: real data from API
        bestSellerAdapter = BestSellerAdapter(requireContext(), arrayListOf()) {
            //update state menu cart in main activity
            activity!!.invalidateOptionsMenu()
        }

        ui.rvBestSeller.adapter = bestSellerAdapter
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
        fun newInstance() = MainFragment()
    }
}