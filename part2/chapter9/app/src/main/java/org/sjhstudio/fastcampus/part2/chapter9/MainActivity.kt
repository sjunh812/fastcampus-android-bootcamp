package org.sjhstudio.fastcampus.part2.chapter9

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sjhstudio.fastcampus.part2.chapter9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}