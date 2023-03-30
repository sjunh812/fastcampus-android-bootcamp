package org.sjhstudio.fastcampus.part2.chapter6.util

object Constants {

    // FCM Server Key
    const val FCM_SERVER_URL = "https://fcm.googleapis.com/fcm/send"
    const val FCM_SERVER_KEY = "AAAAm7mnjYM:APA91bEFiqRyp3jV0tRK4gRtF7vBpbvMbWY_gBb4vXlEHbCwb__WV2FCls0xit3VP6xj7u3N3K15Kzeo427DMqCOOrLQsFy_YfexHyrh4uu1pim8tdQxP2XpFimlVPYAAcOzUxjiSse9"

    // DB
    const val DB_URL = "https://fastcampus-part2-chapter-fc0c6-default-rtdb.firebaseio.com/"
    const val DB_USERS = "Users"
    const val DB_CHAT_ROOMS = "ChatRooms"
    const val DB_CHATS = "Chats"

    // DB Users
    const val DB_USERS_USER_ID = "userId"
    const val DB_USERS_USER_NAME = "userName"
    const val DB_USERS_DESCRIPTION = "description"
    const val DB_USERS_FCM_TOKEN = "fcmToken"

    // DB ChatRooms
    const val DB_CHAT_ROOMS_LAST_MESSAGE = "lastMessage"
    const val DB_CHAT_ROOMS_CHAT_ROOM_ID = "chatRoomId"
    const val DB_CHAT_ROOMS_OTHER_USER_NAME = "otherUserName"
    const val DB_CHAT_ROOMS_OTHER_USER_ID = "otherUserId"
}