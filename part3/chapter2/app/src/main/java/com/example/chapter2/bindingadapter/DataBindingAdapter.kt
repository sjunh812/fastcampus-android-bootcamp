package com.example.chapter2.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.chapter2.R

@BindingAdapter(value = ["code_text", "code_index"])
fun ImageView.setPin(codeText: CharSequence?, index: Int) {
    codeText?.let { text ->
        setImageResource(
            if (text.length > index) {
                R.drawable.ic_circle_black
            } else {
                R.drawable.ic_circle_gray
            }
        )
    }
}