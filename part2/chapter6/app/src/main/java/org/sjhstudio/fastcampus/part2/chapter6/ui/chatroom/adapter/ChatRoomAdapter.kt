package org.sjhstudio.fastcampus.part2.chapter6.ui.chatroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ItemChatRoomBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.ChatRoom

class ChatRoomAdapter : ListAdapter<ChatRoom, ChatRoomAdapter.ChatRoomViewHolder>(diffCallback) {

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

        fun bind(chatRoom: ChatRoom) {
            with(binding) {

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