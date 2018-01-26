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

    init {
        circlePaint.color = Color.MAGENTA
        circlePaint.strokeWidth = 5.0f
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

        drawCircles(canvas)
    }

    private var dragging: Boolean = false

    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var lastX: Float = 0f
    private var lastY: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event!!
//        Log.d("ChoiceView", "onDragEvent(action=" + event.action + ")")
        when (event.action) {
            MotionEvent.ACTION_UP -> {
//                Log.d("ChoiceView", "ACTION_UP")
                dragging = false
            }
            MotionEvent.ACTION_DOWN -> {
//                Log.d("ChoiceView", "ACTION_DOWN")
                dragging = true
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
//                Log.d("ChoiceView", "ACTION_MOVE")
                val deltaX = event.x - lastX
                val deltaY = event.y - lastY
                centerX += deltaX
                centerY += deltaY
                lastX = event.x
                lastY = event.y
            }
//            MotionEvent.ACTION_OUTSIDE -> Log.d("ChoiceView", "ACTION_OUTSIDE")
            else -> {
//                Log.d("ChoiceView", "?")
                return super.onTouchEvent(event)
            }
        }
        invalidate()
        return true
    }


    fun drawCircles(canvas: Canvas) {
        canvas.drawArc(RectF(0f + centerX, 0f + centerY, 200f + centerX, 200f + centerY), 0f, 360f, true, circlePaint)
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
                    return centerX
                }
            val y: Float
                get() {
                    return centerY
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
        }

        fun setViewPortCenter(x: Float, y: Float) {
            viewPortCenterX = x
            viewPortCenterY = y
        }
    }
}
