package org.sjhstudio.fastcampus.part1.chapter8.ui

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.sjhstudio.fastcampus.part1.chapter8.data.FrameItem
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ActivityFrameBinding
import org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.FrameAdapter

class FrameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val images = (intent.getStringArrayExtra("images") ?: emptyArray())
            .map { uriStr -> FrameItem(Uri.parse(uriStr)) }
        val frameAdapter = FrameAdapter(images)

        binding.vpFrame.adapter = frameAdapter
    }
}