package com.example.chapter2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter2.databinding.ActivityMainBinding
import com.example.chapter2.util.AppSignatureHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this

        // Get hash key
        AppSignatureHelper(this).apply {
            Log.e("sjh", "hash : $appSignature")
        }
    }

    fun openShuffle() {
        startActivity(Intent(this, PinActivity::class.java))
    }

    fun openVerifyOTP() {
        startActivity(Intent(this, IdentityInputActivity::class.java))
    }
}