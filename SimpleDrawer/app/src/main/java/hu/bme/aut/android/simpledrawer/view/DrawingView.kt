package hu.bme.aut.android.simpledrawer.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.bme.aut.android.simpledrawer.model.Line
import hu.bme.aut.android.simpledrawer.model.Point

class DrawingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    companion object {
        const val DRAWING_STYLE_LINE = 1
        const val DRAWING_STYLE_POINT = 2
        const val DRAWING_COLOR_RED = Color.RED
        const val DRAWING_COLOR_GREEN = Color.GREEN
        const val DRAWING_COLOR_BLUE = Color.BLUE
    }

    var currentDrawingStyle = DRAWING_STYLE_LINE
    var lines: MutableList<Line> = mutableListOf()
    var points: MutableList<Point> = mutableListOf()

    var currentPaintColor = DRAWING_COLOR_GREEN
        set(value) { field = value; initPaint() }
    private lateinit var paint: Paint
    private var startPoint: Point? = null
    private var endPoint: Point? = null

    init {
        initPaint()
    }

    private fun initPaint() {
        paint = Paint()
        paint.color = currentPaintColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        endPoint = Point(event.x, event.y, currentPaintColor)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> startPoint = Point(event.x, event.y, currentPaintColor)
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                when (currentDrawingStyle) {
                    DRAWING_STYLE_POINT -> addPointToTheList(endPoint!!)
                    DRAWING_STYLE_LINE -> addLineToTheList(startPoint!!, endPoint!!)
                }
                startPoint = null
                endPoint = null
            }
            else -> return false
        }
        invalidate()
        return true
    }

    private fun addPointToTheList(startPoint: Point) {
        points.add(startPoint)
    }

    private fun addLineToTheList(startPoint: Point, endPoint: Point) {
        lines.add(Line(startPoint, endPoint))
    }

    private fun getPaintFromPoint(p: Point): Paint {
        val localPaint = Paint()
        localPaint.color = p.color
        localPaint.style = Paint.Style.STROKE
        localPaint.strokeWidth = 5F
        return localPaint
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (point in points) {
            drawPoint(canvas, point)
        }
        for (line in lines) {
            drawLine(canvas, line.start, line.end)
        }
        when (currentDrawingStyle) {
            DRAWING_STYLE_POINT -> drawPoint(canvas, endPoint)
            DRAWING_STYLE_LINE -> drawLine(canvas, startPoint, endPoint)
        }
    }

    private fun drawPoint(canvas: Canvas, point: Point?) {
        if(point != null) {
            canvas.drawPoint(point.x, point.y, getPaintFromPoint(point))
        }
    }

    private fun drawLine(canvas: Canvas, startPoint: Point?, endPoint: Point?) {
        if (startPoint == null || endPoint == null) return
        canvas.drawLine(
            startPoint.x,
            startPoint.y,
            endPoint.x,
            endPoint.y,
            getPaintFromPoint(startPoint)
        )
    }

    fun restoreObjects(points: MutableList<Point>?, lines: MutableList<Line>?) {
        points?.also { this.points = it }
        lines?.also { this.lines = it }
        invalidate()
    }
}
