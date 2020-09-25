package com.example.mappoints.ui.search


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mappoints.R
import com.example.mappoints.databinding.FragmentSearchAddressBinding
import com.example.mappoints.utils.Navigation
import com.example.mappoints.utils.dismissKeyboard
import com.example.mappoints.utils.setToolbar
import com.example.mappoints.utils.showSnackbar
import com.example.mappoints.viewmodel.SearchAddressViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchAddressFragment : Fragment() {

    val searchViewModel: SearchAddressViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentSearchAddressBinding.inflate(inflater, container, false)
        val resources = requireContext().resources
        binding.apply {
            setToolbar(toolbar)

            toolbar.title = ""
            toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            setSearchClickListener {
                if(editAddress.text!!.isEmpty()){
                    showSnackbar(root, resources.getString(R.string.search_error_no_text))
                }else {
                    searchViewModel.getAddress(editAddress.text.toString(), resources.getString(R.string.google_api_key))
                }
            }
            searchViewModel.loading.observe(viewLifecycleOwner){
                btnSearch.isEnabled = !it
                if(it){
                    progress.visibility = View.VISIBLE
                }else{
                    progress.visibility = View.GONE
                }
            }
            searchViewModel.isSuccess.observe(viewLifecycleOwner){
                if(it){
                    Navigation.backToRoot(this@SearchAddressFragment)
                    requireContext().dismissKeyboard()
                }
            }
            searchViewModel.errorMessage.observe(viewLifecycleOwner){
                if(it!=null){
                    when(it){
                        SearchAddressViewModel.STATUS_ZERO -> showSnackbar(root, resources.getString(R.string.search_error_no_result))
                        else -> showSnackbar(root, resources.getString(R.string.search_error))
                    }
                }
            }
        }
        return binding.root
    }

}