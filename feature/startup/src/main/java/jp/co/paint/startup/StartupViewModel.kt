package jp.co.paint.startup

import android.util.Log
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

    companion object {
        val TAG = StartupViewModel::class.simpleName
    }

    private val mutableInitFinished = MutableLiveData<Unit>()
    val initFinished: LiveData<Unit>
        get() = mutableInitFinished

    fun initialize() {
        viewModelScope.launch {
            delay(3000L)
            Log.d(TAG, "Initialize finished")
            mutableInitFinished.postValue(Unit)
        }
    }

}