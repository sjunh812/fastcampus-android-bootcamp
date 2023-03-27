package org.sjhstudio.fastcampus.part2.chapter6.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.R
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter6.ui.chatroom.ChatRoomFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.mypage.MyPageFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.user.UserFragment
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private var currentUser: FirebaseUser? = null
    private val userFragment: UserFragment by lazy { UserFragment() }
    private val chatRoomFragment: ChatRoomFragment by lazy { ChatRoomFragment() }
    private val myPageFragment: MyPageFragment by lazy { MyPageFragment() }

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onResume() {
        super.onResume()
        checkAuthentication()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database(DB_URL).reference
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun checkAuthentication() {
        currentUser = Firebase.auth.currentUser
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}