package org.sjhstudio.fastcampus.part2.chapter6.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.ui.LoginActivity
import org.sjhstudio.fastcampus.part2.chapter6.util.Constants.DB_URL
import org.sjhstudio.fastcampus.part2.chapter6.util.showToastMessage

open class BaseActivity<T : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> T
) : AppCompatActivity() {

    protected lateinit var binding: T
    protected lateinit var database: DatabaseReference
    protected val userId: String? by lazy { Firebase.auth.currentUser?.uid }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Firebase.database(DB_URL).reference
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)

        checkAuthentication()
    }

    private fun checkAuthentication() {
        if (userId == null) {
            showToastMessage("로그인 세션이 만료되었습니다.")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}