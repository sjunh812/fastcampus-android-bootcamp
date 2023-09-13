package org.sjhstudio.fastcampus.part2.chapter12

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var exoPlayer: ExoPlayer? = null
    private val videoListAdapter by lazy {
        VideoListAdapter {
            // transition test
            binding.layoutMotion.setTransition(R.id.collapse, R.id.expand)
            binding.layoutMotion.transitionToEnd()

            play(it)
        }
    }
    private var mockVideoList: VideoList? = null

    override fun onStart() {
        super.onStart()
        initExoPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initData()

        // motion test
        binding.layoutMotion.jumpToState(R.id.collapse)
    }

    override fun onResume() {
        super.onResume()
        initExoPlayer()
    }

    override fun onStop() {
        exoPlayer?.pause()
        super.onStop()
    }

    override fun onDestroy() {
        exoPlayer?.release()
        super.onDestroy()
    }

    private fun initViews() {
        with(binding) {
            rvVideoList.run {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = videoListAdapter
            }
        }
    }

    private fun initExoPlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(this).build()
                .also {
                    binding.videoPlayerView.player = it
                }
        }
    }

    private fun play(videoItem: VideoItem) {
        exoPlayer?.run {
            setMediaItem(MediaItem.fromUri(Uri.parse(videoItem.videoUrl)))
            prepare()
            play()
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