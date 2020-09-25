package com.example.mappoints.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class ButtonEnabled : AppCompatButton {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context,
        attributeSet,
        defStyle)

    override fun setEnabled(enabled: Boolean) {
        alpha = if (enabled) {
            1f
        } else {
            0.5f
        }
        super.setEnabled(enabled)
    }

}