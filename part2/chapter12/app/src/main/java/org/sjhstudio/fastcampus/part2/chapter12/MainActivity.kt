package org.sjhstudio.fastcampus.part2.chapter12

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var exoPlayer: ExoPlayer? = null
    private val videoListAdapter by lazy {
        VideoListAdapter {
            onClickVideoItem(it)
        }
    }
    private val playerVideoListAdapter by lazy {
        PlayerVideoListAdapter {
            onClickPlayerVideoItem(it)
        }
    }
    private val mockVideoList by lazy {
        readData<VideoList>(VIDEO_LIST_FILE_NAME) ?: VideoList()
    }

    override fun onStart() {
        super.onStart()
        initExoPlayer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initMotionLayout()
        videoListAdapter.submitList(mockVideoList.videos)
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
            rvVideoList.adapter = videoListAdapter
            rvVideoPlayer.adapter = playerVideoListAdapter
            layoutMotion.targetView = videoPlayerContainer
            btnControl.setOnClickListener {
                exoPlayer?.let { player ->
                    if (player.isPlaying) {
                        player.pause()
                    } else {
                        player.play()
                    }
                }
            }
            btnHide.setOnClickListener {
                layoutMotion.transitionToState(R.id.hide)
                exoPlayer?.pause()
            }
        }
    }

    private fun initExoPlayer() {
        if (exoPlayer == null) {
            exoPlayer = ExoPlayer.Builder(this)
                .build()
                .also {
                    binding.videoPlayerView.player = it
                    it.addListener(object : Player.Listener {
                        override fun onIsPlayingChanged(isPlaying: Boolean) {
                            super.onIsPlayingChanged(isPlaying)
                            binding.btnControl.setImageResource(
                                if (isPlaying) {
                                    R.drawable.ic_pause_24
                                } else {
                                    R.drawable.ic_play_arrow_24
                                }
                            )
                        }
                    })
                }
        }
    }

    private fun initMotionLayout() {
        with(binding.layoutMotion) {
            jumpToState(R.id.hide)
            targetView = binding.videoPlayerContainer
            setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {}

                override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                    binding.videoPlayerView.useController = false
                }

                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    binding.videoPlayerView.useController = (currentId == R.id.expand)
                }

                override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {}
            })
        }
    }

    private fun play(videoItem: VideoItem) {
        exoPlayer?.run {
            binding.tvVideoPlayerTitle.text = videoItem.title
            setMediaItem(MediaItem.fromUri(Uri.parse(videoItem.videoUrl)))
            prepare()
            play()
        }
    }

    private fun onClickVideoItem(videoItem: VideoItem) {
        binding.layoutMotion.setTransition(R.id.collapse, R.id.expand)
        binding.layoutMotion.transitionToEnd()
        // 선택된 아이템를 리스트 제일 앞으로 위치
        val list = listOf(videoItem) + mockVideoList.videos.filter { it.id != videoItem.id }
        playerVideoListAdapter.submitList(list)
        play(videoItem)
    }

    private fun onClickPlayerVideoItem(videoItem: VideoItem) {
        // 선택된 아이템를 리스트 제일 앞으로 위치
        val list = listOf(videoItem) + mockVideoList.videos.filter { it.id != videoItem.id }
        playerVideoListAdapter.submitList(list)
        play(videoItem)
    }

    companion object {
        private const val VIDEO_LIST_FILE_NAME = "videos.json"
    }
}