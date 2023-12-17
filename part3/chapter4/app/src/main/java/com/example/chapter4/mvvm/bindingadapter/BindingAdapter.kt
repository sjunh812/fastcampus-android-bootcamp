package com.example.chapter4.mvvm.bindingadapter

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.chapter4.mvvm.model.Image

@BindingAdapter("image")
fun ImageView.setImage(image: Image?) {
    image?.let {
        setBackgroundColor(Color.parseColor(image.color))
        load(image.url) { crossfade(300) }
    }
}