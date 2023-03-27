package org.sjhstudio.fastcampus.part2.chapter6.ui.chat.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ItemMyChatBinding
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ItemOtherChatBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.Chat
import org.sjhstudio.fastcampus.part2.chapter6.model.User

class ChatAdapter : ListAdapter<Chat, ViewHolder>(diffCallback) {

    var otherUser: User? = null

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat) =
                oldItem.chatId == newItem.chatId

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat) =
                oldItem == newItem
        }

        private const val VIEW_TYPE_MY_CHAT = 0
        private const val VIEW_TYPE_OTHER_CHAT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].userId == otherUser?.userId) return VIEW_TYPE_OTHER_CHAT else VIEW_TYPE_MY_CHAT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MY_CHAT -> {
                MyChatViewHolder(
                    ItemMyChatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                OtherChatViewHolder(
                    ItemOtherChatBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    otherUser
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is MyChatViewHolder -> holder.bind(currentList[position])
            is OtherChatViewHolder -> holder.bind(currentList[position])
        }
    }
}

class OtherChatViewHolder(
    private val binding: ItemOtherChatBinding,
    private val otherUser: User?
) : ViewHolder(binding.root) {

    fun bind(chat: Chat) {
        with(binding) {
            tvUserName.text = otherUser?.userName
            tvMessage.text = chat.message
        }
    }
}

class MyChatViewHolder(private val binding: ItemMyChatBinding) :
    ViewHolder(binding.root) {

    fun bind(chat: Chat) {
        with(binding) {
            tvMessage.text = chat.message
        }
    }
}