package com.example.mappoints.ui.detail

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mappoints.R
import com.example.mappoints.databinding.FragmentPointDetailBinding
import com.example.mappoints.db.Point
import com.example.mappoints.utils.*
import com.example.mappoints.viewmodel.PointDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception

@AndroidEntryPoint
class PointDetailFragment : Fragment() {

    val args: PointDetailFragmentArgs by navArgs()
    val pointDetailViewModel: PointDetailViewModel by viewModels()
    var currentPoint: Point? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val binding = FragmentPointDetailBinding.inflate(inflater, container, false)
        val pointId = args.pointId

        binding.apply {

            setToolbar(toolbar)
            toolbar.setNavigationOnClickListener { backAction() }
            toolbar.title = ""

            //observe current point if exist
            pointDetailViewModel.getPoint(pointId).observe(viewLifecycleOwner) { point ->
                if (point != null) {
                    editName.setText(point.name)
                    editLat.setText(point.lat.toString())
                    editLon.setText(point.lng.toString())
                    currentPoint = point
                }
                setEditClickListener { editClick(this, point) }
            }

            setTosearchClickListener {
                Navigation.toSearchAddress(this@PointDetailFragment)
            }

            if (pointId != -1L) {
                btnCreate.text =
                    requireContext().resources.getString(R.string.point_detail_btn_edit)
            }
        }
        return binding.root
    }

    private fun backAction() {
        findNavController().popBackStack()
        requireContext().dismissKeyboard()
    }

    private fun editClick(binding: FragmentPointDetailBinding, point: Point?) {
        binding.apply {
            try {
                val latlng =
                    Validation.validateCoordinate(editLat.text.toString(), editLon.text.toString())
                val updatedPoint = point?.copy(lat = latlng.first,
                    lng = latlng.second,
                    name = editName.text.toString()) ?: Point(lat = latlng.first,
                    lng = latlng.second,
                    name = editName.text.toString())
                pointDetailViewModel.insertPoint(updatedPoint)
                backAction()
            } catch (e: Exception) {
                showSnackbar(root,
                    requireContext().resources.getString(R.string.point_detail_wrong_coordinate))
                Log.e(LOG_TAG, e.message.toString())
            }
        }

    }

    private fun deleteClick(point: Point?) {
        if (point != null) {
            DeleteDialog.create(point.id).show(requireActivity().supportFragmentManager, "delete_dialog")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(args.pointId != -1L) {
            inflater.inflate(R.menu.detail_menu, menu)
        }else {
            super.onCreateOptionsMenu(menu, inflater)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                deleteClick(currentPoint)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    companion object {
        private const val LOG_TAG = "PointDetailFragment_LOG"
    }
}