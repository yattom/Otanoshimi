package jp.yattom.otanosimi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.max

class ChoiceView : View {
    class DragTrack {
        var lastX: Float = 0f
        var lastY: Float = 0f
        var centerX: Float = 0f
        var centerY: Float = 0f
        var dragging: Boolean = false

        fun start(x: Float, y: Float) {
            dragging = true
            lastX = x
            lastY = y
        }

        fun moveTo(x: Float, y: Float) {
            centerX += x - lastX
            centerY += y - lastY
            lastX = x
            lastY = y
        }

        fun end() {
            dragging = false
        }
    }

    private val circlePaint: Paint = Paint()
    private val dragTrack: DragTrack
    private val circleGroup: CirclrGroup

    init {
        circlePaint.color = Color.MAGENTA
        circlePaint.strokeWidth = 5.0f
        circleGroup = CirclrGroup(RectF(0f, 0f, 500f, 500f), 3)
        dragTrack = DragTrack()
        dragTrack.moveTo(250f, 250f)
    }

    @Suppress("unused")
    constructor(context: Context?) : super(context)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    @Suppress("unused")
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!
        val paint = Paint()
        paint.color = Color.CYAN
        paint.textSize = 100f
        canvas.drawText("ChoiceView here", 0.0f, 100.0f, paint)

        circleGroup.setViewPortCenter(-dragTrack.centerX, -dragTrack.centerY)
        drawCircles(canvas)
    }

    private var dragging: Boolean = false

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var lastX: Float = 0f
    private var lastY: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event!!
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                dragTrack.end()
            }
            MotionEvent.ACTION_DOWN -> {
                dragging = true
                dragTrack.start(event.x, event.y)
            }
            MotionEvent.ACTION_MOVE -> {
                dragTrack.moveTo(event.x, event.y)
            }
            else -> {
                return super.onTouchEvent(event)
            }
        }
        invalidate()
        return true
    }


    fun drawCircles(canvas: Canvas) {
        for (c in circleGroup.circles) {
            val x = c.x
            val y = c.y
            canvas.drawArc(x - 100f, y - 100f, x + 100f, y + 100f, 0f, 360f, true, circlePaint)
        }
    }

    class CirclrGroup(val viewPort: RectF, numberOfCircles: Int) {
        inner class Circle(r: Double, d: Double) {
            val centerX: Float
            val centerY: Float

            init {
                centerX = (r * Math.sin(d / 360 * (2 * Math.PI))).toFloat()
                centerY = (-r * Math.cos(d / 360 * (2 * Math.PI))).toFloat()
            }

            val x: Float
                get() {
                    return centerX - viewPortCenterX
                }
            val y: Float
                get() {
                    return centerY - viewPortCenterY
                }
        }

        val circles: List<Circle>
        var viewPortCenterX: Float = 0f
        var viewPortCenterY: Float = 0f

        init {
            val r = (max(viewPort.width(), viewPort.height()) / 2) * Math.sqrt(2.0)
            circles = mutableListOf()
            for (i in 0..numberOfCircles - 1) {
                val c = Circle(r, 360.0 / numberOfCircles * i)
                circles.add(c)
            }

            setViewPortCenter((viewPort.left + viewPort.right) / 2, (viewPort.top + viewPort.bottom) / 2)
        }

        fun setViewPortCenter(x: Float, y: Float) {
            viewPortCenterX = x
            viewPortCenterY = y
        }
    }
}
