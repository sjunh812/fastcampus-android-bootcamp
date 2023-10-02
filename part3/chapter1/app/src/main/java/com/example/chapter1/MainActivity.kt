package com.example.chapter1

import android.graphics.PointF
import android.graphics.RectF
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.util.SizeF
import androidx.core.view.isVisible
import com.example.chapter1.databinding.ActivityMainBinding
import com.example.face_recognition.Camera
import com.example.face_recognition.recognition.FaceAnalyzerListener

class MainActivity : AppCompatActivity(), FaceAnalyzerListener {

    private lateinit var binding: ActivityMainBinding

    private val camera by lazy {
        Camera(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        initViews()

        camera.initCamera(binding.flCamera, this@MainActivity)
    }

    private fun initViews() {
        with(binding) {
            setProgressText("시작하기를 눌러주세요.")

            btnStartDetect.setOnClickListener {
                it.isVisible = false
                camera.startFaceDetect()
                setProgressText("얼굴을 보여주세요.")
            }
        }
    }

    override fun detect() {
    }

    override fun stopDetect() {
        camera.stopFaceDetect()
        reset()
    }

    override fun notDetect() {
    }

    override fun detectProgress(progress: Float, message: String) {
        setProgressText(message)
    }

    override fun faceSize(rectF: RectF, sizeF: SizeF, pointF: PointF) {
    }

    private fun reset() {
        binding.btnStartDetect.isVisible = true
    }

    private fun setProgressText(text: String) {
        TransitionManager.beginDelayedTransition(binding.root)
        binding.tvProgress.text = text
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}