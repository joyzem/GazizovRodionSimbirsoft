package com.example.androidpractice.core.ui.spans

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class ClickableText(private val onClick: () -> Unit) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = true
    }

    override fun onClick(p0: View) {
        this.onClick()
    }
}
