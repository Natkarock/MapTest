package com.example.mappoints.ui.main.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.mappoints.R
import com.example.mappoints.adapters.PointListAdapter
import com.example.mappoints.databinding.FragmentPointListBinding
import com.example.mappoints.utils.Navigation
import com.example.mappoints.viewmodel.PointListViewModel

class PointListFragment : Fragment() {

    private val pointListViewModel: PointListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPointListBinding.inflate(inflater, container, false)
        binding.apply {

            //create point adapter and observe from live data
            val adapter = PointListAdapter(requireContext())
            recyclerview.adapter = adapter

            pointListViewModel.points.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            //create item decorator
            val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!
            decorator.setDrawable(drawable)
            recyclerview.addItemDecoration(decorator)
        }
        return binding.root
    }

    companion object {
        fun create(): PointListFragment {
            return PointListFragment()
        }
    }

}