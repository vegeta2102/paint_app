package jp.co.paint.app

import android.annotation.SuppressLint
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import jp.co.paint.TomatoStateStorePref
import jp.co.paint.core.extentions.Visibility
import jp.co.paint.model.TomatoState

/**
 * Created by vegeta
 */
class PaintViewModel @ViewModelInject constructor(
    private val tomatoStateStorePref: TomatoStateStorePref
) : ViewModel() {

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

    private val _requestSaveImage = MutableLiveData<Unit>()
    val requestSaveImage: LiveData<Unit>
        get() = _requestSaveImage

    private val _requestLoadImage = MutableLiveData<Unit>()
    val requestLoadImage: LiveData<Unit>
        get() = _requestLoadImage

    private val _tomatoVisibility = MutableLiveData<Visibility>()
    val tomatoVisibility: LiveData<Visibility>
        get() = _tomatoVisibility

    private val _firstLoad = MutableLiveData<TomatoState>()
    val firstLoad: LiveData<TomatoState> = _firstLoad

    fun bind(drawingView: DrawingView) {
        drawingViewHolder = drawingView
        _guidanceText.postValue("ペイント")
        tomatoStateStorePref.tomatoState?.let {
            _firstLoad.postValue(it)
        }
        loadImage()
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
        _tomatoVisibility.postValue(Visibility.INVISIBLE)
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

    fun saveImage() {
        _requestSaveImage.postValue(Unit)
    }

    fun loadImage() {
        tomatoStateStorePref.tomatoState?.let {
            _tomatoVisibility.value = Visibility.VISIBLE
        }
        _requestLoadImage.postValue(Unit)
    }

    fun addTomato() {
        val view = drawingViewHolder ?: return
        if (tomatoStateStorePref.tomatoState == null) {
            tomatoStateStorePref.tomatoState = TomatoState(
                posX = view.width.div(2).toFloat(),
                posY = view.height.div(2).toFloat()
            )
            _firstLoad.postValue(tomatoStateStorePref.tomatoState)
        }
        _tomatoVisibility.postValue(Visibility.VISIBLE)
    }

    fun removeTomato() {
        _tomatoVisibility.postValue(Visibility.INVISIBLE)
    }

    override fun onCleared() {
        super.onCleared()
        drawingViewHolder = null
    }
}