package org.sjhstudio.fastcampus.part2.chapter6.ui.chatroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.sjhstudio.fastcampus.part2.chapter6.databinding.FragmentChatRoomBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.ChatRoom
import org.sjhstudio.fastcampus.part2.chapter6.ui.base.BaseFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.ChatActivity
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.ChatActivity.Companion.EXTRA_CHAT_ROOM_ID
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.ChatActivity.Companion.EXTRA_OTHER_USER_ID
import org.sjhstudio.fastcampus.part2.chapter6.ui.chatroom.adapter.ChatRoomAdapter
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHAT_ROOMS

class ChatRoomFragment : BaseFragment<FragmentChatRoomBinding>(FragmentChatRoomBinding::inflate) {

    private val chatRoomAdapter: ChatRoomAdapter by lazy {
        ChatRoomAdapter { chatRoom ->
            navigateToChatActivity(chatRoom)
        }
    }

    companion object {
        private const val LOG = "ChatRoomFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeChatRoom()
    }

    private fun initViews() {
        with(binding) {
            rvChatRoom.apply {
                adapter = chatRoomAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun observeChatRoom() {
        if (userId == null) return

        database.child(DB_CHAT_ROOMS).child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chatRoomList = snapshot.children.map { dataSnapshot ->
                        dataSnapshot.getValue(ChatRoom::class.java)
                    }
                    chatRoomAdapter.submitList(chatRoomList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(LOG, error.message)
                }
            })
    }

    private fun navigateToChatActivity(chatRoom: ChatRoom) {
        val intent = Intent(requireContext(), ChatActivity::class.java)
            .apply {
                putExtra(EXTRA_CHAT_ROOM_ID, chatRoom.chatRoomId)
                putExtra(EXTRA_OTHER_USER_ID, chatRoom.otherUserId)
            }
        startActivity(intent)
    }
}