package org.sjhstudio.fastcampus.part2.chapter12

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val videoListAdapter by lazy {
        VideoListAdapter {
            binding.layoutMotion.setTransition(R.id.collapse, R.id.expand)
            binding.layoutMotion.transitionToEnd()
        }
    }
    private var mockVideoList: VideoList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initData()

        // motion test
        binding.layoutMotion.jumpToState(R.id.collapse)
    }

    private fun initViews() {
        with(binding) {
            rvVideoList.run {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = videoListAdapter
            }
        }
    }

    private fun initData() {
        mockVideoList = readData<VideoList>(VIDEO_LIST_FILE_NAME)
        videoListAdapter.submitList(mockVideoList?.videos ?: emptyList())
    }

    companion object {
        private const val VIDEO_LIST_FILE_NAME = "videos.json"
    }
}