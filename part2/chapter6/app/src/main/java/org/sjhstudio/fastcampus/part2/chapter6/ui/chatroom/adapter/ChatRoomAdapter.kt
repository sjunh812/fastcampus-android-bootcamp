package org.sjhstudio.fastcampus.part2.chapter6.ui.chatroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ItemChatRoomBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.ChatRoom

class ChatRoomAdapter(
    private val onClick: (ChatRoom) -> Unit
) : ListAdapter<ChatRoom, ChatRoomAdapter.ChatRoomViewHolder>(diffCallback) {

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ChatRoom>() {
            override fun areItemsTheSame(oldItem: ChatRoom, newItem: ChatRoom) =
                oldItem.chatRoomId == newItem.chatRoomId

            override fun areContentsTheSame(oldItem: ChatRoom, newItem: ChatRoom) =
                oldItem == newItem
        }
    }

    inner class ChatRoomViewHolder(private val binding: ItemChatRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                adapterPosition.takeIf { pos -> pos != RecyclerView.NO_POSITION }
                    ?.let { position ->
                        onClick.invoke(currentList[position])
                    }
            }
        }

        fun bind(chatRoom: ChatRoom) {
            with(binding) {
                tvUserName.text = chatRoom.otherUserName
                tvLastMessage.text = chatRoom.lastMessage
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomViewHolder {
        return ChatRoomViewHolder(
            ItemChatRoomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatRoomViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}