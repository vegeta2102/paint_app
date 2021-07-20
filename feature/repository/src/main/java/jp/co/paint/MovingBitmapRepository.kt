package jp.co.paint

import androidx.lifecycle.LiveData

/**
 * Created by vegeta
 */
interface MovingBitmapRepository {
    val isTouch: LiveData<Boolean>
    fun set()
}