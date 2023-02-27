package org.sjhstudio.fastcampus.part1.chapter6

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import org.sjhstudio.fastcampus.part1.chapter6.databinding.ActivityMainBinding
import org.sjhstudio.fastcampus.part1.chapter6.databinding.DialogCountdownSettingBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var timer: Timer? = null
    private var deciSec = 0
    private var countdownSec = 10
    private var countdownDeciSec = countdownSec * 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvCountdown.text = String.format("%02d", countdownSec)
        binding.tvCountdown.setOnClickListener {
            showCountdownSettingDialog()
        }

        binding.fabPlay.setOnClickListener {
            updateFloatingActionButtonVisibility(false)
            play()
        }
        binding.fabStop.setOnClickListener {
            showExitDialog()
        }
        binding.fabPause.setOnClickListener {
            updateFloatingActionButtonVisibility(true)
            pause()
        }
        binding.fabLap.setOnClickListener {
            lap()
        }
    }

    private fun play() {
        timer = timer(period = 100) {
            if (countdownDeciSec == 0) {    // 타이머
                deciSec += 1
                val tick = deciSec.rem(10)
                val minute = deciSec.div(10) / 60
                val second = deciSec.div(10) % 60

                runOnUiThread { // Worker Thread 에서 UI 처리방법 1
                    binding.tvTick.text = tick.toString()
                    binding.tvTime.text = String.format("%02d:%02d", minute, second)
                    binding.groupCountdown.isVisible = false
                }
            } else {    // 카운트다운
                countdownDeciSec -= 1
                val second = countdownDeciSec.div(10)
                val progress = ((countdownDeciSec / (countdownSec * 10f)) * 100).toInt()

//                binding.root.post { // Worker Thread 에서 UI 처리방법 2
//                    binding.tvCountdown.text = String.format("%02d", second)
//                    binding.progressbarCountdown.progress = progress
//                }
                Handler(mainLooper).post { // Worker Thread 에서 UI 처리방법 3
                    binding.tvCountdown.text = String.format("%02d", second)
                    binding.progressbarCountdown.progress = progress
                }
            }

            if (deciSec == 0 && countdownDeciSec < 31 && countdownDeciSec % 10 == 0) {
                val toneType =
                    if (countdownDeciSec == 0) ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK
                    else ToneGenerator.TONE_CDMA_ABBR_ALERT
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                    .startTone(toneType, 100)
            }
        }
    }

    private fun stop() {
        deciSec = 0
        binding.tvTime.text = "00:00"
        binding.tvTick.text = "0"
        binding.groupCountdown.isVisible = true
        binding.containerLap.removeAllViews()
        initCountdownDeciSecond()
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun lap() {
        if (countdownDeciSec != 0) return

        TextView(this).apply {
            val number = binding.containerLap.childCount + 1
            val minute = deciSec.div(10) / 60
            val second = deciSec.div(10) % 60
            val deciSecond = deciSec % 10
            text = "$number. ${String.format("%02d:%02d %01d", minute, second, deciSecond)}"
            textSize = 20f
            gravity = Gravity.CENTER
            setPadding(30)
        }.let { tv ->
            binding.containerLap.addView(tv, 0)
        }
    }

    private fun initCountdownDeciSecond() {
        countdownDeciSec = countdownSec * 10
        binding.tvCountdown.text = String.format("%02d", countdownSec)
    }

    private fun updateFloatingActionButtonVisibility(init: Boolean) {
        binding.fabPlay.isVisible = init
        binding.fabStop.isVisible = init
        binding.fabPause.isVisible = init.not()
        binding.fabLap.isVisible = init.not()
    }

    private fun showCountdownSettingDialog() {
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            with(dialogBinding.numberPickerCountdown) {
                maxValue = 20
                minValue = 0
                value = countdownSec
            }
            setView(dialogBinding.root)
            setTitle("카운트다운 설정")
            setPositiveButton("확인") { _, _ ->
                countdownSec = dialogBinding.numberPickerCountdown.value
                initCountdownDeciSecond()
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                stop()
            }
            setNegativeButton("아니오", null)
        }.show()
    }
}