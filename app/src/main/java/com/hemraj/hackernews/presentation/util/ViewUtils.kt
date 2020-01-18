package com.hemraj.hackernews.presentation.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.airbnb.lottie.LottieAnimationView

fun EditText.afterTextChange(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun LottieAnimationView.startAnimation() {
    this.visibility = View.VISIBLE
    this.playAnimation()
}

fun LottieAnimationView.stopAnimation() {
    this.pauseAnimation()
    this.visibility = View.GONE
}
