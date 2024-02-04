package com.example.part3.chapter5.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.part3.chapter5.databinding.ItemImageBinding
import com.example.part3.chapter5.model.ImageItem
import com.example.part3.chapter5.model.ListItem

class ImageViewHolder(
    private val binding: ItemImageBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        (item as? ImageItem)?.let { binding.item = it }
    }
}