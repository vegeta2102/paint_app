package jp.co.paint.app.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import jp.co.paint.DisplayInfoRepository
import jp.co.paint.MovingBitmapRepository
import jp.co.paint.TomatoStateStorePref
import jp.co.paint.model.TomatoState
import javax.inject.Inject
import kotlin.math.abs

/**
 * Created by vegeta
 */
@AndroidEntryPoint
@WithFragmentBindings
class MovableFrameLayoutButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        private const val CLICK_DRAG_TOLERANCE = 10f
    }

    private var downRawX = 0f
    private var downRawY = 0f
    private var dX = 0f
    private var dY = 0f

    @Inject
    lateinit var tomatoStateStorePref: TomatoStateStorePref

    @Inject
    lateinit var movingBitmapRepository: MovingBitmapRepository

    @Inject
    lateinit var displayInfoRepository: DisplayInfoRepository

    init {
        setOnTouchListener(onTouchListener())
    }

    private fun onTouchListener() = object : OnTouchListener {
        private val MIN_CLICK_DURATION = 200L
        private var startClickTime: Long = 0
        private var longClickActive = false

        override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
            val layoutParams = view.layoutParams as MarginLayoutParams
            return when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!longClickActive) {
                        longClickActive = true
                        startClickTime = System.currentTimeMillis()
                    }
                    downRawX = motionEvent.rawX
                    downRawY = motionEvent.rawY
                    dX = view.x - downRawX
                    dY = view.y - downRawY
                    true // Consumed
                }
                MotionEvent.ACTION_UP -> {
                    longClickActive = false
                    val upRawX = motionEvent.rawX
                    val upRawY = motionEvent.rawY
                    val upDX = upRawX - downRawX
                    val upDY = upRawY - downRawY
                    if (abs(upDX) < CLICK_DRAG_TOLERANCE && abs(upDY) < CLICK_DRAG_TOLERANCE) {
                        val clickDuration: Long = System.currentTimeMillis() - startClickTime
                        if (clickDuration >= MIN_CLICK_DURATION) {
                            view.performLongClick()
                        } else {
                            // Click
                            view.performClick()
                        }
                    } else {
                        // drag
                        true
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if (longClickActive) {
                        val clickDuration: Long = System.currentTimeMillis() - startClickTime
                        if (clickDuration >= MIN_CLICK_DURATION) {
                            longClickActive = false
                        } else {
                            return true
                        }
                    }
                    movingBitmapRepository.set()
                    val viewWidth: Int = view.width
                    val viewHeight: Int = view.height
                    val viewParent: View = view.parent as View
                    val parentWidth: Int = viewParent.width
                    var offset = displayInfoRepository.latestData?.offset ?: 40
                    offset = if (offset > 40) {
                        offset
                    } else {
                        offset + viewHeight.div(2)
                    }
                    val parentHeight: Int = viewParent.height - offset
                    var newX = motionEvent.rawX + dX
                    newX = layoutParams.leftMargin.toFloat().coerceAtLeast(newX)
                    newX =
                        (parentWidth - viewWidth - layoutParams.rightMargin.toFloat()).coerceAtMost(
                            newX
                        )
                    var newY = motionEvent.rawY + dY
                    newY = layoutParams.topMargin.toFloat().coerceAtLeast(newY)
                    newY =
                        (parentHeight - viewHeight - layoutParams.bottomMargin.toFloat()).coerceAtMost(
                            newY
                        )
                    view.animate().x(newX).y(newY).setDuration(0).start()
                    tomatoStateStorePref.tomatoState =
                        TomatoState(
                            posX = newX,
                            posY = newY
                        )
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}
