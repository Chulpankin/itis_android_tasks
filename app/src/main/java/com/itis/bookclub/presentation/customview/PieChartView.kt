package com.itis.bookclub.presentation.customview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.itis.bookclub.R
import kotlin.math.*

class PieChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE
        textSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, 14f, resources.displayMetrics
        )
        textAlign = Paint.Align.CENTER
        typeface = Typeface.DEFAULT
    }

    private var sectorData: List<Pair<Int, Int>> = emptyList()
    private var colors: List<Int> = emptyList()
    private var activeSector: Int? = null

    private var showText: Boolean = true
    private var segmentThickness: Float = 0f
    private var selectionOffset: Float = 0f

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PieChartView,
            0,
            0
        ).apply {
            try {
                showText =
                    getBoolean(R.styleable.PieChartView_showText, true)
                segmentThickness =
                    getDimension(R.styleable.PieChartView_segmentThickness, 0f)
                selectionOffset =
                    getDimension(R.styleable.PieChartView_selectionOffset, 20f)
            } finally {
                recycle()
            }
        }
    }

    fun setData(data: List<Pair<Int, Int>>, colors: List<Int>) {
        val total = data.sumOf { it.second }
        require(total == 100) { "Total percentage must equal 100" }
        require(colors.size >= data.size) { "Not enough colors provided" }
        this.sectorData = data
        this.colors = colors
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (sectorData.isEmpty()) return

        val size = width.coerceAtMost(height)
        val radius = size / 2f - 20f
        val innerRadius = if (segmentThickness > 0f) radius - segmentThickness else radius * 0.5f
        val cx = width / 2f
        val cy = height / 2f
        var startAngle = -90f

        for ((index, pair) in sectorData.withIndex()) {
            val sweep = pair.second * 3.6f
            val isActive = index == activeSector
            val midAngle = Math.toRadians((startAngle + sweep / 2).toDouble())
            val offset = if (isActive) selectionOffset else 0f
            val dx = (offset * cos(midAngle)).toFloat()
            val dy = (offset * sin(midAngle)).toFloat()

            paint.color = colors[index]

            val oval = RectF(
                cx - radius + dx,
                cy - radius + dy,
                cx + radius + dx,
                cy + radius + dy
            )

            if (segmentThickness > 0f) {
                val path = Path()
                path.arcTo(oval, startAngle, sweep)
                val innerOval = RectF(
                    cx - innerRadius + dx,
                    cy - innerRadius + dy,
                    cx + innerRadius + dx,
                    cy + innerRadius + dy
                )
                path.arcTo(innerOval, startAngle + sweep, -sweep)
                path.close()
                canvas.drawPath(path, paint)
            } else {
                canvas.drawArc(oval, startAngle, sweep, true, paint)
            }

            if (showText) {
                val labelRadius = (radius + innerRadius) / 2f
                val lx = (cx + dx + labelRadius * cos(midAngle)).toFloat()
                val ly = (cy + dy + labelRadius * sin(midAngle)).toFloat() + textPaint.textSize / 3
                canvas.drawText("${pair.second}%", lx, ly, textPaint)
            }

            startAngle += sweep
        }

        if (segmentThickness <= 0f) {
            paint.color = Color.WHITE
            canvas.drawCircle(cx, cy, radius * 0.5f, paint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_DOWN) return false

        val cx = width / 2f
        val cy = height / 2f
        val dx = event.x - cx
        val dy = event.y - cy
        val distance = sqrt(dx * dx + dy * dy)
        val radius = width.coerceAtMost(height) / 2f - 20f
        val innerR = if (segmentThickness > 0f) radius - segmentThickness else radius * 0.5f

        if (distance !in innerR..radius) {
            if (activeSector != null) {
                activeSector = null
                invalidate()
            }
            return true
        }

        val touchAngle = ((Math.toDegrees(atan2(dy.toDouble(), dx.toDouble())) + 360) % 360).toFloat()
        val normalizedAngle = (touchAngle + 90f) % 360f
        var tempAngle = 0f

        for ((index, pair) in sectorData.withIndex()) {
            val sweep = pair.second * 3.6f
            if (normalizedAngle >= tempAngle && normalizedAngle < tempAngle + sweep) {
                activeSector = if (activeSector == index) null else index
                invalidate()
                return true
            }
            tempAngle += sweep
        }

        return true
    }
}
