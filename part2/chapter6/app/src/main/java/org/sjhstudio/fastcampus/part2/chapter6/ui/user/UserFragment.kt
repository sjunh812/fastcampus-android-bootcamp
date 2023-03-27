package org.sjhstudio.fastcampus.part2.chapter6.ui.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import org.sjhstudio.fastcampus.part2.chapter6.databinding.FragmentUserBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.ChatRoom
import org.sjhstudio.fastcampus.part2.chapter6.model.User
import org.sjhstudio.fastcampus.part2.chapter6.ui.base.BaseFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.ChatActivity
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.ChatActivity.Companion.EXTRA_CHAT_ROOM_ID
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.ChatActivity.Companion.EXTRA_OTHER_USER_ID
import org.sjhstudio.fastcampus.part2.chapter6.ui.user.adapter.UserAdapter
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHAT_ROOMS
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS
import java.util.*

class UserFragment : BaseFragment<FragmentUserBinding>(FragmentUserBinding::inflate) {

    private val userAdapter: UserAdapter by lazy { UserAdapter { user -> navigateToChatActivity(user) } }

    companion object {
        private const val LOG = "UserFragment"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        getUserList()
    }

    private fun initViews() {
        with(binding) {
            rvUser.apply {
                adapter = userAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
    }

    private fun getUserList() {
        database.child(DB_USERS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()

                snapshot.children.forEach { dataSnapshot ->
                    val user = dataSnapshot.getValue(User::class.java) ?: return
                    if (user.userId != userId) userList.add(user)
                }

                userAdapter.submitList(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(LOG, error.message)
            }
        })
    }

    private fun navigateToChatActivity(user: User) {
        // ChatRooms/myUserId/otherUserId(user) 확인
        // 없다면, unique 한 새로운 chatRoomId 생성
        val chatRoomReference =
            database.child(DB_CHAT_ROOMS).child(userId!!).child(user.userId ?: "")

        chatRoomReference.get().addOnSuccessListener { dataSnapshot ->
            val chatRoom = dataSnapshot.getValue(ChatRoom::class.java)
            var chatRoomId = ""

            if (chatRoom != null) {
                // 데이터가 존재
                chatRoomId = chatRoom.chatRoomId ?: ""
            } else {
                chatRoomId = UUID.randomUUID().toString()
                val newChatRoom = ChatRoom(
                    chatRoomId = chatRoomId,
                    otherUserId = user.userId,
                    otherUserName = user.userName
                )
                chatRoomReference.setValue(newChatRoom)
            }

            val intent = Intent(requireContext(), ChatActivity::class.java)
                .apply {
                    putExtra(EXTRA_CHAT_ROOM_ID, chatRoomId)
                    putExtra(EXTRA_OTHER_USER_ID, user.userId)
                }
            startActivity(intent)
        }
    }
}