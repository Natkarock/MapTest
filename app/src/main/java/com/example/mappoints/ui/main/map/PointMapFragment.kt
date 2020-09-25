package com.example.mappoints.ui.main.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mappoints.R
import com.example.mappoints.databinding.FragmentPointMapBinding
import com.example.mappoints.utils.showSnackbar
import com.example.mappoints.viewmodel.PointListViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_point_map.*

@AndroidEntryPoint
class PointMapFragment : Fragment(), OnMapReadyCallback {

    private val pointListViewModel: PointListViewModel by activityViewModels()

    private var googleMap: GoogleMap? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentPointMapBinding.inflate(inflater, container, false)
        binding.apply {
            mapView.onCreate(savedInstanceState)
            mapView.getMapAsync(this@PointMapFragment)
            mapView.onResume()
        }
        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i(LOG_TAG, "Error initialize map")
        }
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onMapReady(p0: GoogleMap?) {
        Log.i(LOG_TAG, "Map initialized")
        googleMap = p0
        googleMap?.apply {
            pointListViewModel.points.observe(viewLifecycleOwner) { points ->
                Log.i(LOG_TAG, "Observe points")
                clear()
                for (point in points) {
                    val latLng = LatLng(point.lat, point.lng)
                    val marker = MarkerOptions().position(latLng)
                        .title(point.name ?: requireContext().resources.getString(
                            R.string.point_default_title) + point.id)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    addMarker(marker)
                }
            }
        }
        enableMyLocation()
        googleMap?.uiSettings?.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
        }
    }


    private fun enableMyLocation() {
        when {
            ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                googleMap?.isMyLocationEnabled = true
            }
            else -> {
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE)
            }
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    enableMyLocation()
                } else {
                    showSnackbar(requireView(),
                        requireContext().resources.getString(R.string.permission_not_available))
                }
                return
            }
            else -> {
            }
        }

    }

    companion object {
        fun create(): PointMapFragment {
            return PointMapFragment()
        }

        private const val LOG_TAG = "Point_Map_Fragment_LOG"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


}