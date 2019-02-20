package com.gogoy.components.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.gogoy.R
import com.gogoy.data.collections.ArrayListItem_Dummy
import com.google.android.material.tabs.TabLayout
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class ItemFragment : Fragment(), ItemContract.View {
    override lateinit var presenter: ItemContract.Presenter
    val ui = ItemFragmentUI<Fragment>()
    private lateinit var itemRelatedAdapter: ItemRelatedAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?):
            View? = ui.createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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