package com.example.face_recognition.recognition

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.view.PreviewView
import androidx.lifecycle.Lifecycle
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

internal class FaceAnalyzer(
    lifecycle: Lifecycle,
    private val previewView: PreviewView,
    private val listener: FaceAnalyzerListener?
) : ImageAnalysis.Analyzer {

    private var widthScaleFactor = 1F
    private var heightScaleFactor = 1F

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)      // 성능
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)                   // 윤곽선
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)     // 표정
        .setMinFaceSize(0.4F)
        .build()

    private val detector = FaceDetection.getClient(options)

    private val successListener = OnSuccessListener<List<Face>> { faces ->

    }

    private val failureListener = OnFailureListener { e ->

    }

    override fun analyze(image: ImageProxy) {
        widthScaleFactor = previewView.width.toFloat() / image.height
        heightScaleFactor = previewView.height.toFloat() / image.width
        detectFaces(image)
    }

    private fun detectFaces(imageProxy: ImageProxy) {
        val image = InputImage.fromMediaImage(
            imageProxy.image,
            imageProxy.imageInfo.rotationDegrees
        )
        detector.process(image)
            .addOnSuccessListener(successListener)
            .addOnFailureListener(failureListener)
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}