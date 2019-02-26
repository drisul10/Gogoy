package com.gogoy.components.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gogoy.data.collections.ArrayListBanner_Dummy
import com.gogoy.data.collections.ArrayListBestSeller_Dummy
import com.gogoy.data.collections.ArrayListCategory
import com.gogoy.data.collections.ArrayListPromo_Dummy
import org.jetbrains.anko.AnkoContext

class MainFragment : Fragment(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter
    private val ui = MainFragmentUI<Fragment>()
    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var promoAdapter: PromoAdapter
    private lateinit var bestSellerAdapter: BestSellerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //start presenter
        presenter.start()
    }

    override fun showBanner() {
        //TODO: real data from API
        bannerAdapter = BannerAdapter(ArrayListBanner_Dummy.list)
        ui.rvBanner.adapter = bannerAdapter
    }

    override fun showCategory() {
        categoryAdapter = CategoryAdapter(ArrayListCategory.list)
        ui.rvCategory.adapter = categoryAdapter
    }

    override fun showPromo() {
        //TODO: real data from API
        promoAdapter = PromoAdapter(requireContext(), ArrayListPromo_Dummy.list) {
            //update state menu cart in main activity
            activity!!.invalidateOptionsMenu()
        }

        ui.rvPromo.adapter = promoAdapter
    }

    override fun showBestSeller() {
        //TODO: real data from API
        bestSellerAdapter = BestSellerAdapter(requireContext(), ArrayListBestSeller_Dummy.list) {
            //update state menu cart in main activity
            activity!!.invalidateOptionsMenu()
        }

        ui.rvBestSeller.adapter = bestSellerAdapter
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}