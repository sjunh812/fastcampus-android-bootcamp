package com.example.chapter2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chapter2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.activity = this
    }

    fun openShuffle() {

    }

    fun openVerifyOTP() {

    }
}