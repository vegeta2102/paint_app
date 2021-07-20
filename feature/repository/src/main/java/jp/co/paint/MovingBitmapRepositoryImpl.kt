package jp.co.paint

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

/**
 * Created by vegeta on 2021/07/20.
 */
class MovingBitmapRepositoryImpl @Inject constructor() : MovingBitmapRepository {
    private val isTouchMutable = MutableLiveData<Boolean>()
    override val isTouch: LiveData<Boolean>
        get() = isTouchMutable

    override fun set() {
        isTouchMutable.postValue(true)
    }
}