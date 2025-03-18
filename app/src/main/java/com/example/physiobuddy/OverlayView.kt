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
import kotlin.math.pow

class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results: PoseLandmarkerResult? = null
    private val pointPaint = Paint().apply {
        color = Color.RED
        strokeWidth = LANDMARK_STROKE_WIDTH
        style = Paint.Style.FILL
    }
    private val linePaint = Paint().apply {
        color = ContextCompat.getColor(context!!, R.color.mp_color_primary)
        strokeWidth = LANDMARK_STROKE_WIDTH
        style = Paint.Style.STROKE
    }
    private val textPaint = Paint().apply {
        color = Color.YELLOW
        textSize = 40f
        typeface = android.graphics.Typeface.DEFAULT_BOLD
    }

    private var scaleFactor: Float = 1f
    private var imageWidth: Int = 1
    private var imageHeight: Int = 1
    private var previousResults: PoseLandmarkerResult? = null

    fun clear() {
        results = null
        invalidate()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        results?.let { poseLandmarkerResult ->
            if (!hasSignificantChange(poseLandmarkerResult)) return

            val landmarks = poseLandmarkerResult.landmarks()
            for (landmark in landmarks) {
                // Draw Points
                for (normalizedLandmark in landmark) {
                    canvas.drawPoint(
                        normalizedLandmark.x() * imageWidth * scaleFactor,
                        normalizedLandmark.y() * imageHeight * scaleFactor,
                        pointPaint
                    )
                }

                // Draw skeleton lines
                PoseLandmarker.POSE_LANDMARKS.forEach {
                    canvas.drawLine(
                        landmarks[0][it!!.start()].x() * imageWidth * scaleFactor,
                        landmarks[0][it.start()].y() * imageHeight * scaleFactor,
                        landmarks[0][it.end()].x() * imageWidth * scaleFactor,
                        landmarks[0][it.end()].y() * imageHeight * scaleFactor,
                        linePaint
                    )
                }

                // Draw elbow angles
                drawAngle(canvas, landmark, 11, 13, 15) // Left Arm
                drawAngle(canvas, landmark, 12, 14, 16) // Right Arm
            }
            previousResults = poseLandmarkerResult
        }
    }

    private fun drawAngle(canvas: Canvas, landmark: List<NormalizedLandmark>, a: Int, b: Int, c: Int) {
        val angle = calculateAngle(landmark[a], landmark[b], landmark[c])
        drawTextOnLandmark(canvas, landmark[b], "${angle.toInt()}°")
    }

    private fun drawTextOnLandmark(canvas: Canvas, landmark: NormalizedLandmark, text: String) {
        val x = landmark.x() * imageWidth * scaleFactor
        val y = landmark.y() * imageHeight * scaleFactor - 20
        canvas.drawText(text, x, y, textPaint)
    }

    private fun hasSignificantChange(newResults: PoseLandmarkerResult): Boolean {
        if (previousResults == null) return true
        val oldLandmarks = previousResults!!.landmarks().flatten()
        val newLandmarks = newResults.landmarks().flatten()
        return oldLandmarks.zip(newLandmarks).any { (old, new) ->
            val dx = old.x() - new.x()
            val dy = old.y() - new.y()
            dx * dx + dy * dy > MOVEMENT_THRESHOLD
        }
    }

    private fun calculateAngle(a: NormalizedLandmark, b: NormalizedLandmark, c: NormalizedLandmark): Double {
        val ab = kotlin.math.sqrt((b.x() - a.x()).pow(2) + (b.y() - a.y()).pow(2))
        val bc = kotlin.math.sqrt((c.x() - b.x()).pow(2) + (c.y() - b.y()).pow(2))
        val ac = kotlin.math.sqrt((c.x() - a.x()).pow(2) + (c.y() - a.y()).pow(2))
        return Math.toDegrees(kotlin.math.acos((ab.pow(2) + bc.pow(2) - ac.pow(2)) / (2 * ab * bc)).toDouble())
    }

    fun setResults(poseLandmarkerResults: PoseLandmarkerResult, imageHeight: Int, imageWidth: Int, runningMode: RunningMode = RunningMode.IMAGE) {
        results = poseLandmarkerResults
        this.imageHeight = imageHeight
        this.imageWidth = imageWidth
        scaleFactor = when (runningMode) {
            RunningMode.IMAGE, RunningMode.VIDEO -> min(width * 1f / imageWidth, height * 1f / imageHeight)
            RunningMode.LIVE_STREAM -> max(width * 1f / imageWidth, height * 1f / imageHeight)
        }
        invalidate()
    }

    companion object {
        private const val LANDMARK_STROKE_WIDTH = 12F
        private const val MOVEMENT_THRESHOLD = 0.001  // Adjust this based on your needs
    }
}
