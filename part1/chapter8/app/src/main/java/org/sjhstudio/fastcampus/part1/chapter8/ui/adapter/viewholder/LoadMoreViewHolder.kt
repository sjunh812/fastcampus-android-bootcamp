package org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ItemLoadMoreBinding

class LoadMoreViewHolder(private val binding: ItemLoadMoreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(onClick: () -> Unit) {
        itemView.setOnClickListener { onClick() }
    }
}