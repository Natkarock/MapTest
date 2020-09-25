package com.example.mappoints.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mappoints.ui.main.list.PointListFragment
import com.example.mappoints.ui.main.map.PointMapFragment


const val MAP_PAGE_INDEX = 0
const val LIST_PAGE_INDEX = 1

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        MAP_PAGE_INDEX to { PointMapFragment.create() },
        LIST_PAGE_INDEX to { PointListFragment.create() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        val fragment = tabFragmentsCreators[position]?.invoke()
        fragment?.retainInstance = true
        return   fragment?: throw IndexOutOfBoundsException()
    }
}