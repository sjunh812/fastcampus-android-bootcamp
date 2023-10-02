package com.example.face_recognition

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.face_recognition.recognition.FaceAnalyzer
import com.example.face_recognition.recognition.FaceAnalyzerListener
import com.google.common.util.concurrent.ListenableFuture
import java.lang.Exception
import java.util.concurrent.Executors

class Camera(private val context: Context) : ActivityCompat.OnRequestPermissionsResultCallback {

    private val preview by lazy {
        Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
    }

    private val cameraSelector by lazy {
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_FRONT)    // 전면 카메라 사용.
            .build()
    }

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var previewView: PreviewView

    private var cameraExecutor = Executors.newSingleThreadExecutor()
    private var listener: FaceAnalyzerListener? = null

    fun initCamera(layout: ViewGroup, listener: FaceAnalyzerListener) {
        this.listener = listener
        previewView = PreviewView(context)
        layout.addView(previewView)
    }

    private fun checkCameraPermission(context: Context) {
        val permissionList = listOf(Manifest.permission.CAMERA)
        if (PermissionUtil.checkPermission(context, permissionList)) {
            openPreview()
        } else {
            PermissionUtil.requestPermission(context as Activity, permissionList, REQ_PERMISSION_CAMERA)
        }
    }

    private fun openPreview() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            .also { providerFuture ->
                providerFuture.addListener({
                    startPreview(context)
                }, ContextCompat.getMainExecutor(context))
            }
    }

    private fun startPreview(context: Context) {
        val cameraProvider = cameraProviderFuture.get()
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                context as LifecycleOwner,
                cameraSelector,
                preview
            )
        } catch (e: Exception) {
            Log.e("sjh", "${e.message}")
        }
    }

    @SuppressLint("RestrictedApi")
    fun startFaceDetect() {
        val cameraProvider = cameraProviderFuture.get()
        val faceAnalyzer = FaceAnalyzer((context as ComponentActivity).lifecycle, previewView, listener)
        val analysisUseCase = ImageAnalysis.Builder()
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, faceAnalyzer)
            }

        try {
            cameraProvider.bindToLifecycle(
                context as LifecycleOwner,
                cameraSelector,
                analysisUseCase
            )
        } catch (e: Exception) {
            Log.e("sjh", "${e.message}")
        }
    }

    fun stopFaceDetect() {
        try {
            cameraProviderFuture.get().unbindAll()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                previewView.releasePointerCapture()
            }
        } catch (e: Exception) {
            Log.e("sjh", "${e.message}")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQ_PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty()) {
                    val deniedPermissions = permissions.filterIndexed { i, _ -> grantResults[i] == PackageManager.PERMISSION_DENIED }
                    if (deniedPermissions.isEmpty()) {
                        openPreview()
                    } else {
                        Toast.makeText(context, "카메라 권한을 허용해야 합니다.", Toast.LENGTH_SHORT).show()
                        (context as Activity).finish()
                    }
                }
            }
        }
    }

    companion object {
        private const val REQ_PERMISSION_CAMERA = 1
    }
}