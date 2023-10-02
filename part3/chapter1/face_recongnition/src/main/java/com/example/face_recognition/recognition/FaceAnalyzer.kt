package com.example.face_recognition.recognition

import android.graphics.PointF
import android.graphics.RectF
import android.util.Log
import android.util.SizeF
import androidx.camera.core.ExperimentalGetImage
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
import kotlin.math.abs

internal class FaceAnalyzer(
    lifecycle: Lifecycle,
    private val previewView: PreviewView,
    private val listener: FaceAnalyzerListener?
) : ImageAnalysis.Analyzer {

    private var widthScaleFactor = 1F
    private var heightScaleFactor = 1F

    private var preCenterX = 0F
    private var preCenterY = 0F
    private var preWidth = 0F
    private var preHeight = 0F

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)      // 성능
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)                   // 윤곽선
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)     // 표정
        .setMinFaceSize(0.4F)
        .build()

    private val detector = FaceDetection.getClient(options)
    private var detectStatus = FaceAnalyzerStatus.UnDetect

    private val successListener = OnSuccessListener<List<Face>> { faces ->
        faces.firstOrNull()?.let { face ->
            if (detectStatus == FaceAnalyzerStatus.UnDetect) {
                // 1. 얼굴 인식을 하지 않은 상태 (최초)
                detectStatus = FaceAnalyzerStatus.Detect
                listener?.detect()
                listener?.detectProgress(25F, "얼굴을 인식했습니다.\n왼쪽 눈만 깜빡여주세요.")
            } else if (detectStatus == FaceAnalyzerStatus.Detect
                && (face.leftEyeOpenProbability ?: 0F) > SUCCESS_VALUE_EYE
                && (face.rightEyeOpenProbability ?: 0F) < SUCCESS_VALUE_EYE
            ) {
                // 2. 왼쪽 윙크를 탐지한 상태
                detectStatus = FaceAnalyzerStatus.LeftWink
                listener?.detectProgress(50F, "오른쪽 눈만 깜빡여주세요.")
            } else if (detectStatus == FaceAnalyzerStatus.LeftWink
                && (face.leftEyeOpenProbability ?: 0F) < SUCCESS_VALUE_EYE
                && (face.rightEyeOpenProbability ?: 0F) > SUCCESS_VALUE_EYE
            ) {
                // 3. 오른쪽 윙크를 탐지한 상태
                detectStatus = FaceAnalyzerStatus.RightWink
                listener?.detectProgress(75F, "활짝 웃어보세요.")
            } else if (detectStatus == FaceAnalyzerStatus.RightWink
                && (face.smilingProbability ?: 0F) > SUCCESS_VALUE_SMILE
            ) {
                // 4. 웃는 얼굴을 인식한 상태 (완료)
                detectStatus = FaceAnalyzerStatus.Smile
                listener?.detectProgress(100F, "얼굴 인식이 완료되었습니다.")
                listener?.stopDetect()
                detector.close()
            } else {
                Log.e("sjh", "Another detector case")
            }

            calDetectSize(face)
        } ?: run {
            if (detectStatus != FaceAnalyzerStatus.UnDetect && detectStatus != FaceAnalyzerStatus.Smile) {
                detectStatus = FaceAnalyzerStatus.UnDetect
                listener?.notDetect()
                listener?.detectProgress(0F, "얼굴을 인식하지 못했습니다.\n처음으로 돌아갑니다.")
            }
        }
    }

    private val failureListener = OnFailureListener { e ->
        Log.e("sjh", "Detect Failed($e)")
        detectStatus = FaceAnalyzerStatus.UnDetect
    }

    init {
        lifecycle.addObserver(detector)
    }

    @ExperimentalGetImage
    override fun analyze(image: ImageProxy) {
        widthScaleFactor = previewView.width.toFloat() / image.height
        heightScaleFactor = previewView.height.toFloat() / image.width
        detectFaces(image)
    }

    @ExperimentalGetImage
    private fun detectFaces(imageProxy: ImageProxy) {
        val image = InputImage.fromMediaImage(
            imageProxy.image ?: return,
            imageProxy.imageInfo.rotationDegrees
        )
        detector.process(image)
            .addOnSuccessListener(successListener)
            .addOnFailureListener(failureListener)
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    private fun calDetectSize(face: Face) {
        val rect = face.boundingBox
        val boxWidth = rect.right - rect.left
        val boxHeight = rect.bottom - rect.top

        val left = rect.right.translateX() - (boxWidth / 2)
        val top = rect.top.translateY() - (boxHeight / 2)
        val right = rect.left.translateX()  + (boxWidth / 2)
        val bottom = rect.bottom.translateY()

        val width = right - left
        val height = bottom - top
        val centerX = left + (width / 2)
        val centerY = top + (height / 2)

        if (abs(preCenterX - centerX) > OFFSET_PIVOT
            || abs(preCenterY - centerY) > OFFSET_PIVOT
            || abs(preWidth - width) > OFFSET_SIZE
            || abs(preHeight - height) > OFFSET_SIZE
        ) {
            listener?.faceSize(
                rectF = RectF(left, top, right, bottom),
                sizeF = SizeF(width, height),
                pointF = PointF(centerX, centerY)
            )

            preCenterX = centerX
            preCenterY = centerY
            preWidth = width
            preHeight = height
        }
    }

    private fun Int.translateX() = previewView.width - (toFloat() * widthScaleFactor)
    private fun Int.translateY() = toFloat() * heightScaleFactor

    companion object {
        private const val SUCCESS_VALUE_EYE = 0.1F
        private const val SUCCESS_VALUE_SMILE = 0.8F

        private const val OFFSET_PIVOT = 15
        private const val OFFSET_SIZE = 30
    }
}