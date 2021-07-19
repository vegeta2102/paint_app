package jp.co.paint.startup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by vegeta
 */
class StartupViewModel @Inject constructor() : ViewModel() {

    private val mutableInitFinished = MutableLiveData<Unit>()
    val initFinished: LiveData<Unit>
        get() = mutableInitFinished

    fun initialize() {
        viewModelScope.launch {
            delay(2000L)
            mutableInitFinished.postValue(Unit)
        }
    }
}