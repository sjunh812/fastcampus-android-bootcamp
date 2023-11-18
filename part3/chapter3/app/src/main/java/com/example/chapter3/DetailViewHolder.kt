package com.example.chapter3

import androidx.recyclerview.widget.RecyclerView
import com.example.chapter3.databinding.ItemDetailBinding
import com.example.chapter3.model.DetailItem

class DetailViewHolder(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DetailItem) {
        binding.item = item
    }
}