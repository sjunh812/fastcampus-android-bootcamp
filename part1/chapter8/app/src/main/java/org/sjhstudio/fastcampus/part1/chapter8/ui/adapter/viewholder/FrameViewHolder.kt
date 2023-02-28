package org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part1.chapter8.data.FrameItem
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ItemFrameBinding

class FrameViewHolder(private val binding: ItemFrameBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(frame: FrameItem) {
        with(binding) {
            ivFrame.setImageURI(frame.uri)
        }
    }
}