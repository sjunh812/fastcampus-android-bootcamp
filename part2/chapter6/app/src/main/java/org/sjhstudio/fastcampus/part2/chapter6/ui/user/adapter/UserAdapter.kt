package org.sjhstudio.fastcampus.part2.chapter6.ui.user.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ItemUserBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.User

class UserAdapter(private val onClick: (User) -> Unit) :
    ListAdapter<User, UserAdapter.UserViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User) =
                oldItem.userId == newItem.userId

            override fun areContentsTheSame(oldItem: User, newItem: User) =
                oldItem == newItem
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                    ?.let { position ->
                        onClick.invoke(currentList[position])
                    }
            }
        }

        fun bind(user: User) {
            with(binding) {
                tvUserName.text = user.userName
                tvDescription.text = user.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}