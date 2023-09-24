package org.sjhstudio.fastcampus.part2.chapter12.main.view

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import org.sjhstudio.fastcampus.part2.chapter12.R
import org.sjhstudio.fastcampus.part2.chapter12.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part2.chapter12.main.data.VideoEntity
import org.sjhstudio.fastcampus.part2.chapter12.main.data.VideoListEntity
import org.sjhstudio.fastcampus.part2.chapter12.main.adapter.VideoListAdapter
import org.sjhstudio.fastcampus.part2.chapter12.player.adapter.PlayerVideoListAdapter
import org.sjhstudio.fastcampus.part2.chapter12.player.model.PlayerVideoModel
import org.sjhstudio.fastcampus.part2.chapter12.player.model.transformToPlayerHeader
import org.sjhstudio.fastcampus.part2.chapter12.player.model.transformToPlayerVideo
import org.sjhstudio.fastcampus.part2.chapter12.player.model.transformsToHeader
import org.sjhstudio.fastcampus.part2.chapter12.util.readData

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
        readData<VideoListEntity>(VIDEO_LIST_FILE_NAME) ?: VideoListEntity()
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
            videoListAdapter.also { rvVideoList.adapter = it }.run { submitList(mockVideoList.videos) }
            rvVideoPlayer.run {
                adapter = playerVideoListAdapter
                itemAnimator = null
            }
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

    private fun play(videoUrl: String, videoTitle: String) {
        exoPlayer?.run {
            binding.tvVideoPlayerTitle.text = videoTitle
            setMediaItem(MediaItem.fromUri(Uri.parse(videoUrl)))
            prepare()
            play()
        }
    }

    private fun onClickVideoItem(videoEntity: VideoEntity) {
        binding.layoutMotion.setTransition(R.id.collapse, R.id.expand)
        binding.layoutMotion.transitionToEnd()
        // 선택된 아이템를 리스트 제일 앞으로 위치
        val list = listOf(videoEntity.transformToPlayerHeader()) + mockVideoList.videos.filter { it.id != videoEntity.id }.map { it.transformToPlayerVideo() }
        playerVideoListAdapter.submitList(list)
        play(videoEntity.videoUrl, videoEntity.title)
    }

    private fun onClickPlayerVideoItem(playerVideo: PlayerVideoModel) {
        // 선택된 아이템를 리스트 제일 앞으로 위치
        val list = listOf(playerVideo.transformsToHeader()) + mockVideoList.videos.filter { it.id != playerVideo.id }.map { it.transformToPlayerVideo() }
        playerVideoListAdapter.submitList(list) {
            // 플레이어 내, 비디오 리스트 갱신완료 후 최상단으로 스크롤
            binding.rvVideoPlayer.scrollToPosition(0)
        }
        play(playerVideo.videoUrl, playerVideo.title)
    }

    companion object {
        private const val VIDEO_LIST_FILE_NAME = "videos.json"
    }
}