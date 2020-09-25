package com.example.mappoints.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mappoints.R
import com.example.mappoints.ui.detail.PointDetailFragmentDirections
import com.example.mappoints.ui.main.MainPagerFragmentDirections

object Navigation {
    fun toDetail(fragment: Fragment,id: Long = -1){
        val direction = MainPagerFragmentDirections.actionMainFragmentToPointDetailFragment(id)
        fragment.findNavController().navigate(direction)
    }

    fun toSearchAddress(fragment: Fragment){
        val direction = PointDetailFragmentDirections.actionPointDetailFragmentToSearchAddressFragment()
        fragment.findNavController().navigate(direction)
    }

    fun backToRoot(fragment: Fragment){
        fragment.findNavController().popBackStack(R.id.mainFragment, false)
    }
}