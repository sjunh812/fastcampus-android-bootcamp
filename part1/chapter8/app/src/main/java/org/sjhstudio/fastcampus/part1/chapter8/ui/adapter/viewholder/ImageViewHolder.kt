package org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part1.chapter8.data.ImageItem
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ItemImageBinding

class ImageViewHolder(private val binding: ItemImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(image: ImageItem.Image) {
        with(binding) {
            ivImage.setImageURI(image.uri)
        }
    }
}