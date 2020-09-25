package com.example.mappoints.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mappoints.R
import com.example.mappoints.adapters.LIST_PAGE_INDEX
import com.example.mappoints.adapters.MAP_PAGE_INDEX
import com.example.mappoints.adapters.MainPagerAdapter
import com.example.mappoints.databinding.FragmentMainBinding
import com.example.mappoints.utils.Navigation
import com.example.mappoints.utils.setToolbar
import com.google.android.material.tabs.TabLayoutMediator

class MainPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.apply {
            setToolbar(toolbar)

            viewPager.adapter = MainPagerAdapter(this@MainPagerFragment)
            viewPager.setUserInputEnabled(false)
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = getTabTitle(position)
            }.attach()

        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add -> {
                Navigation.toDetail(this, -1)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MAP_PAGE_INDEX -> getString(R.string.tab_map)
            LIST_PAGE_INDEX -> getString(R.string.tab_list)
            else -> null
        }
    }
}