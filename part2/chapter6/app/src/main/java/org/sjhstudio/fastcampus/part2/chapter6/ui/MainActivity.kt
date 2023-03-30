package org.sjhstudio.fastcampus.part2.chapter6.ui

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.sjhstudio.fastcampus.part2.chapter6.R
import org.sjhstudio.fastcampus.part2.chapter6.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter6.ui.base.BaseActivity
import org.sjhstudio.fastcampus.part2.chapter6.ui.chatroom.ChatRoomFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.mypage.MyPageFragment
import org.sjhstudio.fastcampus.part2.chapter6.ui.user.UserFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val userFragment: UserFragment by lazy { UserFragment() }
    private val chatRoomFragment: ChatRoomFragment by lazy { ChatRoomFragment() }
    private val myPageFragment: MyPageFragment by lazy { MyPageFragment() }

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
        } else {
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // 권한 허용됨
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // 권한 교육용 팝업 필요
                showNotificationRationaleDialog()
            } else {
                // 권한 요청
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun showNotificationRationaleDialog() {
        AlertDialog.Builder(this)
            .setMessage("알림 권한이 없으면 알림을 받을 수 없습니다.")
            .setPositiveButton("허용") { _, _ -> requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }
            .setNegativeButton("취소", null)
            .show()
    }
}