package com.example.mappoints.ui.detail

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.mappoints.R
import com.example.mappoints.utils.Navigation
import com.example.mappoints.viewmodel.PointDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteDialog : DialogFragment() {

    val pointDetailViewModel: PointDetailViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val resources = requireContext().resources
        val id = arguments?.getLong(POINT_ID_TAG)
        return builder.apply {
            setMessage(resources.getString(R.string.delete_dialog_text))
            setNegativeButton(resources.getString(R.string.delete_dialog_cancel)
            ) { p0, p1 -> }
            setPositiveButton(resources.getString(R.string.delete_dialog_ok)
            ) { p0, p1 ->
                if (id != null) {
                    pointDetailViewModel.deletePointById(id)
                }
                Navigation.backToRoot(this@DeleteDialog)
            }
        }.create()
    }


    companion object {
        const val POINT_ID_TAG = "POINT_ID_TAG"
        fun create(id: Long): DeleteDialog{
            val bundle = Bundle()
            bundle.apply {
                putLong(POINT_ID_TAG, id)
            }
            val dialog = DeleteDialog()
            dialog.arguments = bundle
            return dialog
        }
    }
}