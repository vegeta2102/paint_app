package jp.co.paint.app

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

/**
 * Created by vegeta
 */
class PaintViewModel @ViewModelInject constructor() : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private var drawingViewHolder: DrawingView? = null

    private val _guidanceText = MutableLiveData<String?>()
    val guidanceText: LiveData<String?> = _guidanceText.map {
        it.plus("選択中")
    }

    private val _requestChangeColor = MutableLiveData<Unit>()
    val requestChangeColor: LiveData<Unit>
        get() = _requestChangeColor

    private val _requestChangeThickness = MutableLiveData<Unit>()
    val requestChangeThickness: LiveData<Unit>
        get() = _requestChangeThickness

    fun bind(drawingView: DrawingView) {
        drawingViewHolder = drawingView
        _guidanceText.postValue("ペイント")
    }

    fun erase() {
        drawingViewHolder?.setErase(true)
        _guidanceText.postValue("消しゴム")
    }

    private fun cancelErase() {
        drawingViewHolder?.setErase(false)
    }

    fun startNew() {
        cancelErase()
        drawingViewHolder?.startNew()
        _guidanceText.postValue("ペイント")
    }

    fun changeColor() {
        cancelErase()
        _guidanceText.postValue("ペイント")
        _requestChangeColor.postValue(Unit)
    }

    fun changeThickness() {
        _requestChangeThickness.postValue(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        drawingViewHolder = null
    }
}