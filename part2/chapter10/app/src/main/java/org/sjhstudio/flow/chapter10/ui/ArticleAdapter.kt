package org.sjhstudio.flow.chapter10.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sjhstudio.flow.chapter10.R
import org.sjhstudio.flow.chapter10.databinding.ItemArticleBinding
import org.sjhstudio.flow.chapter10.model.Article

class ArticleAdapter(
    private val onItemClicked: (Article) -> Unit,
    private val onBookmarkClicked: (String, Boolean) -> Unit
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

            binding.btnBookmark.setOnClickListener {
                adapterPosition.takeIf { pos ->
                    pos != RecyclerView.NO_POSITION
                }?.let { position ->
                    val articleId = currentList[position].id
                    onBookmarkClicked.invoke(articleId,currentList[position].isBookmark.not())
                    currentList[position].isBookmark = currentList[position].isBookmark.not()
                    setBookmarkImage(currentList[position].isBookmark)
                }
            }
        }

        fun bind(data: Article) {
            with(binding) {
                Glide.with(ivPhoto)
                    .load(data.imageUrl)
                    .into(ivPhoto)
                tvDescription.text = data.description
                setBookmarkImage(data.isBookmark)
            }
        }

        private fun setBookmarkImage(isBookmark: Boolean) {
            binding.btnBookmark.setImageResource(
                if (isBookmark) R.drawable.ic_bookmark_24 else R.drawable.ic_bookmark_border_24
            )
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