package com.example.androidpractice.core.ui

import android.view.MotionEvent
import android.widget.EditText

fun EditText.setOnEndDrawableClick(onClick: () -> Unit) {
    setOnTouchListener { _, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_UP) {
            val endDrawableWidth =
                compoundDrawables[2]?.bounds?.width() ?: return@setOnTouchListener false
            val clickedOnEndDrawable = motionEvent.rawX >= (right - endDrawableWidth)
            if (clickedOnEndDrawable) {
                onClick()
                performClick()
                return@setOnTouchListener true
            }
        }
        return@setOnTouchListener false
    }
}
