package com.gogoy.components.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.gogoy.R
import com.gogoy.data.models.ItemPromoModel
import com.gogoy.utils.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.delay
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.find

class ItemFragment : Fragment(), ItemContract.View, HandleMessage {

    private lateinit var argId: String
    private lateinit var itemRelatedAdapter: ItemRelatedAdapter
    private var itemsRelated: MutableList<ItemPromoModel> = mutableListOf()
    override lateinit var presenter: ItemContract.Presenter
    override lateinit var rootLayout: RelativeLayout
    private lateinit var sharedPref: SharedPref
    override lateinit var snackbar: Snackbar
    val ui = ItemFragmentUI<Fragment>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        argId = arguments!!.getString(Constant.UP_ID) as String
        sharedPref = SharedPref(requireContext())
        return ui.createView(AnkoContext.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.start()
    }

    override fun onPresenterStart() {
        presenter.requestItem(argId)
        presenter.requestItemsRelated(requireActivity(), argId)
        this.clickButtonBuy()
        this.clickButtonMin()
        this.clickButtonPlus()
        this.progressBarShow(R.id.pb_items_related)
        this.setViewState()
    }

    override fun clickButtonBuy() {
        ui.llBtnBuy.onClick {
            ui.llBtnBuy.backgroundColorResource = R.color.colorPrimaryDark
            delay(50)
            ui.llBtnBuy.backgroundColorResource = R.color.colorPrimary

            sharedPref.cartAddItem(argId to 1)

            //change state
            ui.llBtnBuy.invisible()
            ui.llBtnMinPlus.visible()
            ui.tvTotalPerItem.text = sharedPref.cartAmount(argId).toString()

            //update cart
            activity!!.invalidateOptionsMenu()
        }
    }

    override fun clickButtonMin() {
        ui.btnMin.onClick {
            ui.btnMin.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            ui.btnMin.backgroundColorResource = R.color.colorPrimary

            sharedPref.cartReduceAmountItem(argId to sharedPref.cartAmount(argId))

            if ((ui.tvTotalPerItem.text).toString().toInt() > 1) {
                ui.tvTotalPerItem.text = sharedPref.cartAmount(argId).toString()
            } else {
                ui.llBtnMinPlus.invisible()
                ui.llBtnBuy.visible()

                //update cart
                activity!!.invalidateOptionsMenu()
            }
        }
    }

    override fun clickButtonPlus() {
        ui.btnPlus.onClick {
            ui.btnPlus.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            ui.btnPlus.backgroundColorResource = R.color.colorPrimary

            sharedPref.cartAddAmountItem(argId to sharedPref.cartAmount(argId))

            //change state
            ui.tvTotalPerItem.text = sharedPref.cartAmount(argId).toString()
        }
    }

    override fun progressBarHide(id: Any) {
        when (id) {
            R.id.pb_items_related -> ui.pbItemsRelated.gone()
        }
    }

    override fun progressBarShow(id: Any) {
        when (id) {
            R.id.pb_items_related -> ui.pbItemsRelated.visible()
        }
    }

    override fun setTab(strDesc: String, strInfo: String) {
        val pagerTabAdapter = PagerTabAdapter(requireFragmentManager(), strInfo, strDesc)

        // Set up the ViewPager with the sections adapter.
        val viewPagerTab = view?.findViewById<ViewPager?>(R.id.vp_tab)
        viewPagerTab?.adapter = pagerTabAdapter

        val tabLayout = view?.findViewById<View>(R.id.tab_layout) as TabLayout
        tabLayout.setupWithViewPager(viewPagerTab)
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))

        val bundleDesc = Bundle()
        val bundleInfo = Bundle()
        val tabDescFragment = TabDescFragment()
        val tabInfoFragment = TabInfoFragment.instance()

        bundleDesc.putString(Constant.UP_DESC, strDesc)
        bundleInfo.putString(Constant.UP_INFO, strInfo)

        tabInfoFragment.arguments = bundleInfo
        tabDescFragment.arguments = bundleDesc

        val vpTab = view?.find<ViewPager>(R.id.vp_tab)
        if (vpTab?.currentItem == 0) {
            fragmentManager!!
                .beginTransaction()
                .replace(R.id.fl_tab_content, tabInfoFragment, TabInfoFragment::class.simpleName)
                .commit()
        }

        vpTab?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        fragmentManager!!
                            .beginTransaction()
                            .replace(R.id.fl_tab_content, tabInfoFragment, TabInfoFragment::class.simpleName)
                            .commit()
                    }

                    else -> fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.fl_tab_content, tabDescFragment, TabDescFragment::class.simpleName)
                        .commit()
                }
            }
        })
    }

    override fun setViewState() {
        ui.llBtnBuy.visible()
        ui.llBtnMinPlus.invisible()

        if (sharedPref.cartAmount(argId) > 0) {
            ui.llBtnBuy.invisible()
            ui.llBtnMinPlus.visible()
        }
    }

    override fun showItem(data: ItemPromoModel) {
        ui.tvItemName.text = data.name
        ui.tvItemOwner.text = data.owner_id
        ui.tvItemPrice.text = toRupiah(data.price)
        ui.ivItemBadge.setImageBitmap(decodeImageBase64ToBitmap(data.badge))
        ui.tvTotalPerItem.text = sharedPref.cartAmount(data.id).toString()
    }

    override fun showItemsRelated(data: Collection<ItemPromoModel>) {
        ui.rvItemRelated.visible()
        itemsRelated.clear()
        itemsRelated.addAll(data)

        itemRelatedAdapter = ItemRelatedAdapter(requireContext(), itemsRelated)
        ui.rvItemRelated.adapter = itemRelatedAdapter
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
        fun newInstance() = ItemFragment()
    }
}