package com.example.part3.chapter5.bindingadapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import coil.load
import com.example.part3.chapter5.R
import java.text.SimpleDateFormat
import java.util.Date

@BindingAdapter("visible")
fun View.setVisible(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("image")
fun ImageView.setImage(url: String) {
    load(url) {
        crossfade(300)
    }
}

@SuppressLint("SimpleDateFormat")
@BindingAdapter("date")
fun TextView.setDate(date: Date?) {
    date?.let {
        text = SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(it)
    }
}

@BindingAdapter("favorite")
fun ImageView.setFavorite(isFavorite: Boolean) {
    setImageResource(if (isFavorite) R.drawable.ic_favorite_24 else R.drawable.ic_favorite_border_24)
}