package org.sjhstudio.fastcampus.part2.chapter5.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part2.chapter5.databinding.ItemNewsBinding
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsItem

class NewsAdapter : ListAdapter<NewsItem, NewsAdapter.NewsViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem) =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem) =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            ItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class NewsViewHolder(private val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                    ?.let { position ->

                    }
            }
        }

        fun bind(item: NewsItem) {
            with(binding) {
                tvTitle.text = item.title
            }
        }
    }
}