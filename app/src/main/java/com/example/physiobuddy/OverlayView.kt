package com.example.physiobuddy

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult
import com.google.mediapipe.tasks.components.containers.NormalizedLandmark
import kotlin.math.max
import kotlin.math.min

class OverlayView(context: Context?, attrs: AttributeSet?) :
    View(context, attrs) {

    private var results: PoseLandmarkerResult? = null
    private var pointPaint = Paint()
    private var linePaint = Paint()

    private var scaleFactor: Float = 1f
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1

    init {
        initPaints()
    }

    fun clear() {
        results = null
        pointPaint.reset()
        linePaint.reset()
        invalidate()
        initPaints()
    }

    private fun initPaints() {
        linePaint.color =
            ContextCompat.getColor(context!!, R.color.mp_color_primary)
        linePaint.strokeWidth = LANDMARK_STROKE_WIDTH
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = Color.RED
        pointPaint.strokeWidth = LANDMARK_STROKE_WIDTH
        pointPaint.style = Paint.Style.FILL
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        results?.let { poseLandmarkerResult ->
            for (landmark in poseLandmarkerResult.landmarks()) {
                for (normalizedLandmark in landmark) {
                    canvas.drawPoint(
                        normalizedLandmark.x() * imageWidth * scaleFactor,
                        normalizedLandmark.y() * imageHeight * scaleFactor,
                        pointPaint
                    )
                }

                // Retrieve elbow and shoulder landmarks
                val leftShoulder = landmark[11]  // LEFT_SHOULDER
                val leftElbow = landmark[13]     // LEFT_ELBOW
                val leftWrist = landmark[15]     // LEFT_WRIST

                val rightShoulder = landmark[12] // RIGHT_SHOULDER
                val rightElbow = landmark[14]    // RIGHT_ELBOW
                val rightWrist = landmark[16]    // RIGHT_WRIST

                // Calculate angles
                val poseHelper = PoseLandmarkerHelper(context = context!!)
                val leftAngle = poseHelper.calculateAngle(leftShoulder, leftElbow, leftWrist)
                val rightAngle = poseHelper.calculateAngle(rightShoulder, rightElbow, rightWrist)


                // Draw skeleton lines
                PoseLandmarker.POSE_LANDMARKS.forEach {
                    canvas.drawLine(
                        poseLandmarkerResult.landmarks()[0][it!!.start()].x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks()[0][it.start()].y() * imageHeight * scaleFactor,
                        poseLandmarkerResult.landmarks()[0][it.end()].x() * imageWidth * scaleFactor,
                        poseLandmarkerResult.landmarks()[0][it.end()].y() * imageHeight * scaleFactor,
                        linePaint
                    )
                }

                // Draw elbow angles at elbow positions
                drawTextOnLandmark(canvas, leftElbow, "${leftAngle.toInt()}°")
                drawTextOnLandmark(canvas, rightElbow, "${rightAngle.toInt()}°")
            }
        }
    }
    private fun drawTextOnLandmark(canvas: Canvas, landmark: NormalizedLandmark, text: String) {
        val x = landmark.x() * imageWidth * scaleFactor
        val y = landmark.y() * imageHeight * scaleFactor - 20  // Slightly above the elbow dot
        canvas.drawText(text, x, y, textPaint)
    }
    private val textPaint = Paint().apply {
        color = Color.YELLOW
        textSize = 40f
        typeface = android.graphics.Typeface.DEFAULT_BOLD
    }


    fun setResults(
        poseLandmarkerResults: PoseLandmarkerResult,
        imageHeight: Int,
        imageWidth: Int,
        runningMode: RunningMode = RunningMode.IMAGE
    ) {
        results = poseLandmarkerResults

        this.imageHeight = imageHeight
        this.imageWidth = imageWidth

        scaleFactor = when (runningMode) {
            RunningMode.IMAGE,
            RunningMode.VIDEO -> {
                min(width * 1f / imageWidth, height * 1f / imageHeight)
            }
            RunningMode.LIVE_STREAM -> {
                // PreviewView is in FILL_START mode. So we need to scale up the
                // landmarks to match with the size that the captured images will be
                // displayed.
                max(width * 1f / imageWidth, height * 1f / imageHeight)
            }
        }
        invalidate()
    }

    companion object {
        private const val LANDMARK_STROKE_WIDTH = 12F
    }
}