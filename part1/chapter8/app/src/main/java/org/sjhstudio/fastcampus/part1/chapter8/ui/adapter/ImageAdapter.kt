package org.sjhstudio.fastcampus.part1.chapter8.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part1.chapter8.data.ImageItem
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ItemImageBinding
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ItemLoadMoreBinding
import org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.viewholder.ImageViewHolder
import org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.viewholder.LoadMoreViewHolder

class ImageAdapter(private val loadMoreItemClick: () -> Unit) : ListAdapter<ImageItem, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ITEM_IMAGE -> ImageViewHolder(ItemImageBinding.inflate(layoutInflater, parent, false))
            else -> LoadMoreViewHolder(ItemLoadMoreBinding.inflate(layoutInflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ImageViewHolder -> holder.bind(currentList[position] as ImageItem.Image)
            is LoadMoreViewHolder -> holder.bind(loadMoreItemClick)
        }
    }

    override fun getItemCount(): Int {
        return if (currentList.size == 0) 0 else currentList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position) ITEM_LOAD_MORE else ITEM_IMAGE
    }

    companion object {

        private const val ITEM_IMAGE = 0
        private const val ITEM_LOAD_MORE = 1

        private val diffCallback = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}