package com.example.part3.chapter5.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.part3.chapter5.databinding.ItemImageBinding
import com.example.part3.chapter5.databinding.ItemVideoBinding
import com.example.part3.chapter5.list.viewholder.ImageViewHolder
import com.example.part3.chapter5.list.viewholder.VideoViewHolder
import com.example.part3.chapter5.model.ImageItem
import com.example.part3.chapter5.model.ListItem

class ListAdapter : ListAdapter<ListItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ImageItem -> IMAGE
            else -> VIDEO
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            IMAGE -> ImageViewHolder(ItemImageBinding.inflate(inflater, parent, false))
            else -> VideoViewHolder(ItemVideoBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (getItemViewType(position)) {
            IMAGE -> (holder as ImageViewHolder).bind(item)
            else -> (holder as VideoViewHolder).bind(item)
        }
    }

    companion object {
        private const val IMAGE = 0
        private const val VIDEO = 1

        private val diffCallback = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem) =
                oldItem.thumbnailUrl == newItem.thumbnailUrl

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem) =
                oldItem == newItem
        }
    }
}