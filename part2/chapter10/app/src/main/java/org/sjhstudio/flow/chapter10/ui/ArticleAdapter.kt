package org.sjhstudio.flow.chapter10.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.flow.chapter10.databinding.ItemArticleBinding
import org.sjhstudio.flow.chapter10.model.Article

class ArticleAdapter(
    private val onItemClicked: (Article) -> Unit
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class ArticleViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                adapterPosition.takeIf { pos ->
                    pos != RecyclerView.NO_POSITION
                }?.let { position ->
                    onItemClicked.invoke(currentList[position])
                }
            }
        }

        fun bind(data: Article) {
            with(binding) {
                Glide.with(ivPhoto)
                    .load(data.imageUrl)
                    .into(ivPhoto)

                tvDescription.text = data.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            ItemArticleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}