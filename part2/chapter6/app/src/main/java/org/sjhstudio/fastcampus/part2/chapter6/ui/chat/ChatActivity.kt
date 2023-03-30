package org.sjhstudio.fastcampus.part2.chapter6.ui.chat

import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityChatBinding
import org.sjhstudio.fastcampus.part2.chapter6.model.Chat
import org.sjhstudio.fastcampus.part2.chapter6.model.User
import org.sjhstudio.fastcampus.part2.chapter6.ui.base.BaseActivity
import org.sjhstudio.fastcampus.part2.chapter6.ui.chat.adapter.ChatAdapter
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHATS
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHAT_ROOMS
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHAT_ROOMS_CHAT_ROOM_ID
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHAT_ROOMS_LAST_MESSAGE
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHAT_ROOMS_OTHER_USER_ID
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_CHAT_ROOMS_OTHER_USER_NAME
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_USERS
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.FCM_SERVER_KEY
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.FCM_SERVER_URL
import java.io.IOException

class ChatActivity : BaseActivity<ActivityChatBinding>(ActivityChatBinding::inflate) {

    private val chatRoomId: String? by lazy { intent.getStringExtra(EXTRA_CHAT_ROOM_ID) }
    private val otherUserId: String? by lazy { intent.getStringExtra(EXTRA_OTHER_USER_ID) }
    private val chatAdapter: ChatAdapter by lazy { ChatAdapter() }
    private var userName: String = ""
    private var otherUser: User? = null
    private var chatList = mutableListOf<Chat>()

    companion object {
        private const val LOG = "ChatActivity"
        const val EXTRA_CHAT_ROOM_ID = "chatRoomId"
        const val EXTRA_OTHER_USER_ID = "otherUserId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (chatRoomId == null || otherUserId == null) {
            finish()
            return
        }

        initViews()
        getUser {
            getOtherUser {
                observeChats()
            }
        }
    }

    private fun initViews() {
        with(binding) {
            rvChat.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(this@ChatActivity)
            }

            etMessage.addTextChangedListener { text ->
                btnSend.isEnabled = !text.isNullOrEmpty()
            }

            btnSend.setOnClickListener {
                // 채팅 전송
                val message = etMessage.text.toString()
                val newChat = Chat(
                    message = message,
                    userId = userId
                )
                database.child(DB_CHATS).child(chatRoomId!!)
                    .push()
                    .apply { newChat.chatId = key }
                    .setValue(newChat)

                // ChatRooms DB 갱신
                val updateMap = mutableMapOf<String, Any>(
                    "$DB_CHAT_ROOMS/$userId/$otherUserId/$DB_CHAT_ROOMS_LAST_MESSAGE" to message,
                    "$DB_CHAT_ROOMS/$otherUserId/$userId/$DB_CHAT_ROOMS_LAST_MESSAGE" to message,
                    "$DB_CHAT_ROOMS/$otherUserId/$userId/$DB_CHAT_ROOMS_CHAT_ROOM_ID" to chatRoomId!!,
                    "$DB_CHAT_ROOMS/$otherUserId/$userId/$DB_CHAT_ROOMS_OTHER_USER_NAME" to userName,
                    "$DB_CHAT_ROOMS/$otherUserId/$userId/$DB_CHAT_ROOMS_OTHER_USER_ID" to userId!!,
                )
                database.updateChildren(updateMap)

                sendFcmMessage(userName, message)

                etMessage.text.clear()
            }
        }
    }

    private fun getUser(nextProcess: () -> Unit) {
        // 유저 정보 가져오기
        database.child(DB_USERS).child(userId!!)
            .get()
            .addOnSuccessListener { userDataSnapshot ->
                userName = userDataSnapshot.getValue(User::class.java)?.userName.orEmpty()

                nextProcess.invoke()
            }
    }

    private fun getOtherUser(nextProcess: () -> Unit) {
        // 채팅 상대 정보 가져오기
        database.child(DB_USERS).child(otherUserId!!)
            .get()
            .addOnSuccessListener { otherUserDataSnapshot ->
                val user = otherUserDataSnapshot.getValue(User::class.java)

                otherUser = user
                chatAdapter.otherUser = user
                binding.etMessage.isEnabled = true

                nextProcess.invoke()
            }
    }

    private fun observeChats() {
        // 가져온 유저 정보를 활용하여 채팅 정보 바인딩
        database.child(DB_CHATS).child(chatRoomId!!)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {
                    val chat = snapshot.getValue(Chat::class.java) ?: return
                    chatList.add(chat)
                    chatAdapter.submitList(chatList.toMutableList())
                    binding.rvChat.smoothScrollToPosition(chatList.lastIndex)
                }

                override fun onChildChanged(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {}

                override fun onChildRemoved(snapshot: DataSnapshot) {}

                override fun onChildMoved(
                    snapshot: DataSnapshot,
                    previousChildName: String?
                ) {}

                override fun onCancelled(error: DatabaseError) {
                    Log.d(LOG, error.message)
                }
            })
    }

    private fun sendFcmMessage(title: String, body: String) {
        val okHttpClient = OkHttpClient()

        val rootJson = JSONObject()
            .apply {
                put("to", otherUser?.fcmToken)
                put("priority", "high")
            }
        val notificationJson = JSONObject()
            .apply {
                put("title", title)
                put("body", body)
            }
        rootJson.put("notification", notificationJson)

        val request = Request.Builder()
            .post(rootJson.toString().toRequestBody("application/json; charset=utf-8".toMediaType()))
            .header("Authorization", "key=${FCM_SERVER_KEY}")
            .url(FCM_SERVER_URL)
            .build()
        okHttpClient.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e(LOG, e.message.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(LOG, response.message)
            }
        })
    }
}