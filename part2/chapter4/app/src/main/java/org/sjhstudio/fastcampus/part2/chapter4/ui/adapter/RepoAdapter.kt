package org.sjhstudio.fastcampus.part2.chapter4.ui.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part2.chapter4.databinding.ItemRepoBinding
import org.sjhstudio.fastcampus.part2.chapter4.model.Repo
import org.sjhstudio.fastcampus.part2.chapter4.util.findLanguageColor

class RepoAdapter(private val onClick: (Repo) -> Unit) :
    ListAdapter<Repo, RepoAdapter.RepoViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
        }
    }

    inner class RepoViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                    ?.let { position ->
                        onClick(currentList[position])
                    }
            }
        }

        fun bind(repo: Repo) {
            with(binding) {
                tvName.text = repo.name
                tvDescription.text = repo.description
                tvStarCount.text = repo.startCount.toString()
                tvForkCount.text = repo.forkCount.toString()
                viewLanguage.visibility = if (repo.language != null) View.VISIBLE else View.INVISIBLE
                tvLanguage.visibility = if (repo.language != null) View.VISIBLE else View.INVISIBLE

                repo.language?.let { language ->
                    viewLanguage.backgroundTintList = ColorStateList.valueOf(
                        Color.parseColor(
                            itemView.context.findLanguageColor(language) ?: "#FFFFFF"
                        )
                    )
                    tvLanguage.text = language
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}