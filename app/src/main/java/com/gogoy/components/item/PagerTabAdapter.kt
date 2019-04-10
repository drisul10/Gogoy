package com.gogoy.components.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gogoy.utils.Constant

class PagerTabAdapter(fragment: FragmentManager, private val strInfo: String, private val strDesc: String) :
    FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        val bundleInfo = Bundle()
        bundleInfo.putString(Constant.UP_INFO, strInfo)

        val bundleDesc = Bundle()
        bundleDesc.putString(Constant.UP_DESC, strDesc)

        val tabInfoFragment = TabInfoFragment.instance()
        tabInfoFragment.arguments = bundleInfo

        val tabDescFragment = TabDescFragment.instance()
        tabDescFragment.arguments = bundleInfo

        return when (position) {
            0 -> tabInfoFragment
            else -> tabDescFragment
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> INFO
            else -> DESC
        }
    }

    companion object {
        const val INFO = "Informasi"
        const val DESC = "Deskripsi"
    }
}