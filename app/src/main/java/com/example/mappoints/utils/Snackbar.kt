package com.example.mappoints.utils

import android.view.View
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(root: View, text: String, duration: Int = 3000) {
    val snackbar = Snackbar.make(root, text, duration)
    snackbar.show()
}