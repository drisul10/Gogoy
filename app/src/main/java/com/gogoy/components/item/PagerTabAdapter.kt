package com.gogoy.components.item

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerTabAdapter(fragment: FragmentManager) : FragmentPagerAdapter(fragment) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> TabInfoFragment()
            else -> TabDescFragment()
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