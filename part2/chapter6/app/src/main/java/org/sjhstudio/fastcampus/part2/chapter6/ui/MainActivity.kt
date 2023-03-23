package org.sjhstudio.fastcampus.part2.chapter6.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.sjhstudio.fastcampus.part2.chapter6.R

class MainActivity : AppCompatActivity() {

    private var currentUser: FirebaseUser? = null

    companion object {
        private const val LOG = "MainActivity"
    }

    override fun onResume() {
        super.onResume()
        checkAuthentication()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun checkAuthentication() {
        currentUser = Firebase.auth.currentUser
        Log.d(LOG, "currentUser: ${currentUser?.email}")
        if (currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}