package org.sjhstudio.fastcampus.part1.chapter8.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import org.sjhstudio.fastcampus.part1.chapter8.data.FrameItem
import org.sjhstudio.fastcampus.part1.chapter8.databinding.ActivityFrameBinding
import org.sjhstudio.fastcampus.part1.chapter8.ui.adapter.FrameAdapter

class FrameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.apply {
            title = "나만의 앨범"
            setSupportActionBar(this)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val images = (intent.getStringArrayExtra("images") ?: emptyArray())
            .map { uriStr -> FrameItem(Uri.parse(uriStr)) }
        val frameAdapter = FrameAdapter(images)

        binding.viewPagerFrame.adapter = frameAdapter

        // custom indicator
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPagerFrame
        ) { tab, position ->
            Log.d("tab layout", "tab position : ${tab.position}, position : $position")
//            binding.viewPagerFrame.currentItem = tab.position
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}