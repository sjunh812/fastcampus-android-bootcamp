package org.sjhstudio.fastcampus.part2.chapter6.ui.chat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityChatBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.Chat
import org.sjhstudio.fastcampus.part2.chapter6.model.User
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.adapter.ChatAdapter
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHATS
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_URL
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var database: DatabaseReference
    private val userId: String? by lazy { Firebase.auth.currentUser?.uid }
    private val chatRoomId: String? by lazy { intent.getStringExtra(EXTRA_CHAT_ROOM_ID) }
    private val otherUserId: String? by lazy { intent.getStringExtra(EXTRA_OTHER_USER_ID) }
    private val chatAdapter: ChatAdapter by lazy { ChatAdapter() }

    companion object {
        const val EXTRA_CHAT_ROOM_ID = "chatRoomId"
        const val EXTRA_OTHER_USER_ID = "otherUserId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database(DB_URL).reference
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (userId == null || chatRoomId == null || otherUserId == null) {
            finish()
            return
        }

        initViews()
        getUser()
        getOtherUser()
        observeChats()
    }

    private fun getUser() {
        database.child(DB_USERS).child(userId!!)
            .get()
            .addOnSuccessListener { dataSnapshot ->
                val user = dataSnapshot.getValue(User::class.java)
            }
    }

    private fun getOtherUser() {
        database.child(DB_USERS).child(otherUserId!!)
            .get()
            .addOnSuccessListener { dataSnapshot ->
                chatAdapter.otherUser = dataSnapshot.getValue(User::class.java)
            }
    }

    private fun observeChats() {
        database.child(DB_CHATS).child(chatRoomId!!).push()
            .addChildEventListener(object: ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chat = snapshot.getValue(Chat::class.java)
                    val newChatList = chatAdapter.currentList.toMutableList()
                        .apply { add(chat) }
                    chatAdapter.submitList(newChatList)
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun initViews() {
        with(binding) {
            rvChat.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(this@ChatActivity)
            }
        }
    }
}