package org.sjhstudio.fastcampus.part2.chapter5.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.fastcampus.part2.chapter5.databinding.ItemNewsBinding
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsItem
import org.sjhstudio.fastcampus.part2.chapter5.model.NewsModel

class NewsAdapter : ListAdapter<NewsModel, NewsAdapter.NewsViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NewsModel>() {
            override fun areItemsTheSame(oldItem: NewsModel, newItem: NewsModel) =
                oldItem === newItem

            override fun areContentsTheSame(oldItem: NewsModel, newItem: NewsModel) =
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

        fun bind(item: NewsModel) {
            with(binding) {
                tvTitle.text = item.title

                Glide.with(itemView.context)
                    .load(item.imageUrl)
                    .into(ivThumbnail)
            }
        }
    }
}