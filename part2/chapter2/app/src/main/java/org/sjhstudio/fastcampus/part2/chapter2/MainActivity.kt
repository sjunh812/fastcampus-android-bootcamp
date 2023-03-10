package org.sjhstudio.fastcampus.part2.chapter2

import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.sjhstudio.fastcampus.part2.chapter2.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity(), OnTimerTickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var timer: Timer
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var recordFileName: String = ""
    private var recordState: RecordState = RecordState.RELEASE

    // MediaPlayer seek
    private var position = 0

    companion object {
        private const val REQUEST_RECORD_AUDIO = 200
        private const val LOG = "AudioRecordTest"
    }

    /**
     * 녹음 순서
     * 1. release → recording → release
     * 2. release → playing → release
     */
    enum class RecordState {
        RELEASE, RECORDING, PLAYING, PAUSE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Record to the external cache directory for visibility
        recordFileName = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"
        timer = Timer(this)

        initViews()
    }

    override fun onStop() {
        stopPlaying()
        stopRecording()
        super.onStop()
    }

    private fun initViews() {
        with(binding) {
            btnRecord.setOnClickListener {
                when (recordState) {
                    RecordState.RELEASE -> record() // with permission check
                    RecordState.RECORDING -> stopRecording()
                    else -> {} // do nothing
                }
            }
            btnPlay.setOnClickListener {
                when (recordState) {
                    RecordState.RELEASE -> startPlaying()
                    RecordState.PAUSE -> resumePlaying()
                    RecordState.PLAYING -> pausePlaying()
                    else -> {} // do nothing
                }
            }
            btnStop.setOnClickListener {
                when (recordState) {
                    RecordState.PLAYING, RecordState.PAUSE -> stopPlaying()
                    else -> {} // do nothing
                }
            }
        }
    }

    private fun updateRecordViews() {
        if (recordState == RecordState.PLAYING || recordState == RecordState.PAUSE) return

        val recordButtonDrawableId =
            if (recordState == RecordState.RELEASE) R.drawable.ic_record_24 else R.drawable.ic_stop_24
        val recordButtonColor =
            if (recordState == RecordState.RELEASE) R.color.red else R.color.black
        val playButtonAlpha = if (recordState == RecordState.RELEASE) 1.0f else 0.3f

        with(binding) {
            btnRecord.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        recordButtonDrawableId
                    )
                )
                imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this@MainActivity, recordButtonColor)
                )
            }
            btnPlay.apply {
                isEnabled = recordState == RecordState.RELEASE
                alpha = playButtonAlpha
            }
        }
    }

    private fun updatePlayViews() {
        if (recordState == RecordState.RECORDING) return

        val playButtonDrawableId =
            if (recordState == RecordState.PLAYING) R.drawable.ic_pause_24 else R.drawable.ic_play_24
        val recordButtonAlpha = if (recordState == RecordState.RELEASE) 1.0f else 0.3f

        with(binding) {
            btnPlay.setImageDrawable(
                ContextCompat.getDrawable(
                    this@MainActivity,
                    playButtonDrawableId
                )
            )
            btnRecord.apply {
                isEnabled = recordState == RecordState.RELEASE
                alpha = recordButtonAlpha
            }
        }
    }

    private fun record() {
        when {
            checkSelfPermission(RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED -> {
                startRecording()
            }
            shouldShowRequestPermissionRationale(RECORD_AUDIO) -> {
                showPermissionRationaleDialog()
            }
            else -> {
                requestPermissions(arrayOf(RECORD_AUDIO), REQUEST_RECORD_AUDIO)
            }
        }
    }

    private fun startRecording() {
        recordState = RecordState.RECORDING

        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(recordFileName)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            try {
                prepare()
            } catch (e: IOException) {
                Log.e(LOG, "[MediaRecorder] prepare() failed")
            }

            start()
        }

        binding.viewWaveForm.clearData()    // 녹음된 maxAmplitude 리스트 초기화
        timer.start()

        updateRecordViews()
    }

    private fun stopRecording() {
        recordState = RecordState.RELEASE

        recorder?.run {
            stop()
            release()
        }
        recorder = null

        timer.stop()

        updateRecordViews()
    }

    private fun startPlaying() {
        recordState = RecordState.PLAYING

        player = MediaPlayer().apply {
            try {
                setDataSource(recordFileName)
                prepare()
            } catch (e: IOException) {
                Log.e(LOG, "[MediaPlayer] prepare() failed")
            } catch (e: IllegalArgumentException) {
                Log.e(LOG, "[MediaPlayer] setDataSource() failed")
            }

            start()
        }
        player?.setOnCompletionListener {
            Log.i(LOG, "[MediaPlayer] play completed")
            stopPlaying()
        }

        binding.viewWaveForm.clearWave()    // 그려진 녹음 파형 초기화(maxAmplitude 유지)
        timer.start()

        updatePlayViews()
    }

    private fun resumePlaying() {
        recordState = RecordState.PLAYING
        Log.i(LOG, "[MediaPlayer] play resumed(position : $position)")

        player?.run {
            seekTo(position)
            start()
        }

        timer.start()

        updatePlayViews()
    }

    private fun pausePlaying() {
        recordState = RecordState.PAUSE
        position = player?.currentPosition ?: 0
        Log.i(LOG, "[MediaPlayer] play paused(position : $position)")

        player?.pause()

        timer.pause()

        updatePlayViews()
    }

    private fun stopPlaying() {
        recordState = RecordState.RELEASE

        player?.run {
            stop()
            release()
        }
        player = null

        timer.stop()

        updatePlayViews()
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.description_record_audio_permission_rationale))
            setPositiveButton(getString(R.string.label_grant)) { _, _ ->
                requestPermissions(
                    arrayOf(RECORD_AUDIO),
                    REQUEST_RECORD_AUDIO
                )
            }
            setNegativeButton(getString(R.string.label_cancel), null)
        }.show()
    }

    private fun showPermissionSettingsDialog() {
        AlertDialog.Builder(this).apply {
            setMessage(getString(R.string.description_record_audio_permission_settings))
            setPositiveButton(getString(R.string.label_change)) { _, _ -> navigateToAppSettings() }
            setNegativeButton(getString(R.string.label_cancel), null)
        }.show()
    }

    private fun navigateToAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .apply { data = Uri.fromParts("package", packageName, null) }
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val audioPermissionGranted = requestCode == REQUEST_RECORD_AUDIO
                && grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (audioPermissionGranted) {
            startRecording()
        } else if (shouldShowRequestPermissionRationale(RECORD_AUDIO)) {
            showPermissionRationaleDialog()
        } else {
            showPermissionSettingsDialog()
        }
    }

    override fun onTick(duration: Long) {
        Log.i(LOG, "onTick() : $duration")

        val millisecond = duration % 1000
        val second = (duration / 1000) % 60
        val minute = (duration / 1000 / 60)
        val time = String.format("%02d:%02d.%d", minute, second, millisecond / 100)

        binding.tvTime.text = time

        if (recordState == RecordState.PLAYING) {
            binding.viewWaveForm.replyAmplitude()
        } else {
            binding.viewWaveForm.addAmplitude(recorder?.maxAmplitude?.toFloat() ?: 0f)
        }
    }
}