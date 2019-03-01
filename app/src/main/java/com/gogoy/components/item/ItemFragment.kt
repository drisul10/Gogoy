package com.gogoy.components.item

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.gogoy.R
import com.gogoy.data.collections.ArrayListItem_Dummy
import com.gogoy.data.models.ItemCartModel
import com.gogoy.utils.Prefs
import com.gogoy.utils.invisible
import com.gogoy.utils.toRupiah
import com.gogoy.utils.visible
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.delay
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk27.coroutines.onClick

class ItemFragment : Fragment(), ItemContract.View {
    override lateinit var presenter: ItemContract.Presenter
    val ui = ItemFragmentUI<Fragment>()
    private lateinit var itemRelatedAdapter: ItemRelatedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId: String? = arguments!!.getString("ID")
        val itemName: String? = arguments!!.getString("NAME")
        val itemOwner: String? = arguments!!.getString("OWNER")
        val itemPrice: Int = arguments!!.getInt("PRICE")
        val itemBadge: Int = arguments!!.getInt("BADGE")
        val prefs = Prefs(requireContext())
        var listItem = prefs.getPref()
        var indexListItem = 0
        var totalPerItem = 0

        for ((j, i) in listItem.withIndex()) {
            if (i.id == itemId) {
                totalPerItem = listItem[j].totalPerItem
            }
        }

        ui.tvItemName.text = itemName
        ui.tvItemOwner.text = itemOwner
        ui.tvItemPrice.text = toRupiah(itemPrice)
        ui.ivItemBadge.setImageResource(itemBadge)
        ui.tvTotalPerItem.text = totalPerItem.toString()

        //set state
        ui.llBtnBuy.visible()
        ui.llBtnMinPlus.invisible()

        if (totalPerItem > 0) {
            ui.tvTotalPerItem.text = totalPerItem.toString()
            ui.llBtnBuy.invisible()
            ui.llBtnMinPlus.visible()
        }

        ui.llBtnBuy.onClick {
            ui.llBtnBuy.backgroundColorResource = R.color.colorPrimaryDark
            delay(50)
            ui.llBtnBuy.backgroundColorResource = R.color.colorPrimary

            //keep listItem up to date
            listItem = prefs.getPref()
            totalPerItem = 1

            //check if sharedPref size 0 or data is exist then add new item list to sharedPref
            if (prefs.getPref().size == 0 || !prefs.existId(itemId!!)) {
                listItem.add(ItemCartModel(itemId!!, itemName!!, itemPrice, itemOwner!!, itemBadge, totalPerItem))
                prefs.setPref(listItem)
            }

            //state when clicked btn buy
            ui.tvTotalPerItem.text = totalPerItem.toString()
            ui.llBtnBuy.invisible()
            ui.llBtnMinPlus.visible()

            //update cart count
            activity!!.invalidateOptionsMenu()
        }

        ui.btnPlus.onClick {

            ui.btnPlus.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            ui.btnPlus.backgroundColorResource = R.color.colorPrimary

            //keep listItem up to date
            listItem = prefs.getPref()

            // get current totalPerItem when data is exist
            // and then store the index into indexListItem
            for ((j, i) in listItem.withIndex()) {
                if (i.id == itemId) {
                    totalPerItem = i.totalPerItem
                    indexListItem = j
                }
            }

            listItem[indexListItem].totalPerItem += 1
            prefs.setPref(listItem)

            //change state
            ui.tvTotalPerItem.text = listItem[indexListItem].totalPerItem.toString()
        }

        ui.btnMin.onClick {

            ui.btnMin.backgroundColorResource = R.color.colorPrimaryDark
            delay(30)
            ui.btnMin.backgroundColorResource = R.color.colorPrimary

            //keep listItem up to date
            listItem = prefs.getPref()

            if ((ui.tvTotalPerItem.text).toString().toInt() > 1) {
                listItem[indexListItem].totalPerItem -= 1
                prefs.setPref(listItem)

                //change state
                ui.tvTotalPerItem.text = listItem[indexListItem].totalPerItem.toString()
            } else {
                listItem.removeIf { s -> s.id == itemId }
                prefs.setPref(listItem)

                //hide layout btn min&plus and show layout btn buy
                ui.llBtnMinPlus.invisible()
                ui.llBtnBuy.visible()

                //update cart count
                activity!!.invalidateOptionsMenu()
            }
        }

        //start presenter
        presenter.start()

        setTab()

        val vpTab = view.find<ViewPager>(R.id.vp_tab)
        val tabInfoFragment = TabInfoFragment()
        tabInfoFragment.arguments = arguments
        val tabDescFragment = TabDescFragment()
        tabDescFragment.arguments = arguments

        if (vpTab.currentItem == 0) {
            fragmentManager!!
                .beginTransaction()
                .replace(R.id.fl_tab_content, tabInfoFragment, TabInfoFragment::class.simpleName)
                .commit()
        }

        vpTab.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.fl_tab_content, tabInfoFragment, TabInfoFragment::class.simpleName)
                        .commit()

                    else -> fragmentManager!!
                        .beginTransaction()
                        .replace(R.id.fl_tab_content, tabDescFragment, TabDescFragment::class.simpleName)
                        .commit()
                }
            }

        })
    }

    override fun showItemRelated() {
        //TODO: real data from API
        itemRelatedAdapter = ItemRelatedAdapter(ArrayListItem_Dummy.list)
        ui.rvItemRelated.adapter = itemRelatedAdapter
    }

    private fun setTab() {
        val pagerTabAdapter = PagerTabAdapter(requireFragmentManager())

        // Set up the ViewPager with the sections adapter.
        val viewPagerTab = view!!.findViewById<ViewPager?>(R.id.vp_tab)
        viewPagerTab!!.adapter = pagerTabAdapter

        val tabLayout = view!!.findViewById<View>(R.id.tab_layout) as TabLayout
        tabLayout.setupWithViewPager(viewPagerTab)

        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
    }

    companion object {
        fun newInstance() = ItemFragment()
    }
}