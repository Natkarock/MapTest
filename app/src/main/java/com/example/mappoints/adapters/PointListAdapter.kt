package com.example.mappoints.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController


import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mappoints.R
import com.example.mappoints.adapters.PointListAdapter.*
import com.example.mappoints.databinding.LayoutPointItemBinding
import com.example.mappoints.db.Point
import com.example.mappoints.ui.main.MainPagerFragmentDirections

class PointListAdapter(private  val context: Context): ListAdapter<Point, PointViewHolder>(PointDiffCallback()) {

    inner class PointViewHolder(val binding: LayoutPointItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(point: Point){
            binding.apply {
                setClickListener{ navigateToDetail(point, it) }
                val nameText = if (point.name == null || point.name.isEmpty()){
                    context.resources.getString(R.string.point_default_title) + point.id
                }else {
                    point.name
                }
                editName.text =  nameText
                txtLatLng.text = "${point.lat}  ${point.lng}"
            }
        }
    }

    private fun navigateToDetail(point: Point, view: View) {
        val direction = MainPagerFragmentDirections.actionMainFragmentToPointDetailFragment(pointId=point.id)
        view.findNavController().navigate(direction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        return  PointViewHolder(LayoutPointItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        val point = getItem(position)
        holder.bind(point)
    }


}

private class PointDiffCallback : DiffUtil.ItemCallback<Point>() {
    override fun areItemsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Point, newItem: Point): Boolean {
        return oldItem == newItem
    }
}