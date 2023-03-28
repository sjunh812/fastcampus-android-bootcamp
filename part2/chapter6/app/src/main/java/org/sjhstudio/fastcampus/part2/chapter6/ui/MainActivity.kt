package org.sjhstudio.fastcampus.part2.chapter6.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.R
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter6.ui.base.BaseActivity
import org.sjhstudio.fastcampus.part2.chapter6.ui.chatroom.ChatRoomFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.mypage.MyPageFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.user.UserFragment
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_URL

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val userFragment: UserFragment by lazy { UserFragment() }
    private val chatRoomFragment: ChatRoomFragment by lazy { ChatRoomFragment() }
    private val myPageFragment: MyPageFragment by lazy { MyPageFragment() }

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        replaceFragment(userFragment)
    }

    private fun initViews() {
        with(binding) {
            bottomNavigationView.setOnItemSelectedListener {
                return@setOnItemSelectedListener when (it.itemId) {
                    R.id.menu_user -> {
                        replaceFragment(userFragment)
                        true
                    }
                    R.id.menu_chat -> {
                        replaceFragment(chatRoomFragment)
                        true
                    }
                    R.id.menu_my_page -> {
                        replaceFragment(myPageFragment)
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .apply {
                replace(R.id.container_fragment, fragment)
                commit()
            }
    }
}