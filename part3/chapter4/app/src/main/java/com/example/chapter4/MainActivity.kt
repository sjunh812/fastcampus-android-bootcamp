package com.example.chapter4

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chapter4.databinding.ActivityMainBinding
import com.example.chapter4.mvc.MvcActivity
import com.example.chapter4.mvp.MvpActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            it.view = this
        }
    }

    fun openMvc() = startActivity(Intent(this, MvcActivity::class.java))

    fun openMvp() = startActivity(Intent(this, MvpActivity::class.java))

    fun openMvvm() {}

    fun openMvi() {}
}