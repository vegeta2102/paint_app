package jp.co.paint.app

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat


/**
 * Created by vegeta
 */
class DrawingView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // To hold the path that will be drawn.
    private val drawPath by lazy { Path() }

    // Paint object to draw drawPath and drawCanvas.
    private val drawPaint by lazy { Paint() }
    private val canvasPaint by lazy { Paint(Paint.DITHER_FLAG) }

    // initial color
    private var paintColor = -0x1000000
    private var previousColor = paintColor

    // canvas on which drawing takes place.
    private var drawCanvas: Canvas? = null

    // canvas bitmap
    private var canvasBitmap: Bitmap? = null

    // Brush stroke width
    private var brushSize = 0f  // Brush stroke width
    private var lastBrushSize = 0f

    // To enable and disable erasing mode.
    private var erase = false

    init {
        setUpDrawing()
    }

    /**
     * Initialize all objects required for drawing here.
     * One time initialization reduces resource consumption.
     */
    private fun setUpDrawing() {
        drawPaint.color = paintColor
        // Making drawing smooth.
        drawPaint.isAntiAlias = true
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        // Initial brush size is medium.
        brushSize = resources.getInteger(R.integer.medium_size).toFloat()
        lastBrushSize = brushSize
        drawPaint.strokeWidth = brushSize
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(canvasBitmap!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(canvasBitmap!!, 0F, 0F, canvasPaint)
        canvas.drawPath(drawPath, drawPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // X and Y position of user touch.
        val touchX = event.x
        val touchY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> drawPath.moveTo(touchX, touchY)
            MotionEvent.ACTION_MOVE -> drawPath.lineTo(touchX, touchY)
            MotionEvent.ACTION_UP -> {
                if (erase) {
                    drawPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
                }
                drawCanvas?.drawPath(drawPath, drawPaint)
                drawPath.reset()
                drawPaint.xfermode = null
            }
            else -> return false
        }

        // invalidate the view so that canvas is redrawn.
        invalidate()
        return true
    }

    fun loadFile(bitmap: Bitmap) {
        // val bMap = BitmapFactory.decodeFile(Environment.getExternalStorageState() + "/test.png")
        val d: Drawable = BitmapDrawable(resources, bitmap)
        d.setBounds(left, top, right, bottom)
        drawCanvas?.let {
            d.draw(it)
            invalidate()
        }
    }

    fun setColor(newColor: Int) {
        // invalidate the view
        invalidate()
        paintColor = ContextCompat.getColor(this.context, newColor)
        drawPaint.color = paintColor
        previousColor = paintColor
    }

    fun setBrushSize(newSize: Float) {
        val pixelAmount = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            newSize, resources.displayMetrics
        )
        brushSize = pixelAmount
        drawPaint.strokeWidth = brushSize
    }

    fun setLastBrushSize(lastSize: Float) {
        lastBrushSize = lastSize
    }

    fun getLastBrushSize(): Float {
        return lastBrushSize
    }

    fun setErase(isErase: Boolean) {
        //set erase true or false
        erase = isErase
        if (erase) {
            drawPaint.color = Color.WHITE
            //drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            drawPaint.color = previousColor
            drawPaint.xfermode = null
        }
    }

    fun startNew() {
        drawCanvas?.drawColor(0, PorterDuff.Mode.CLEAR)
        invalidate()
    }
}