package jp.co.paint.app

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import jp.co.paint.core.extentions.Visibility
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by vegeta on 2021/07/26.
 */
class ProgressViewModel @ViewModelInject constructor() : ViewModel() {
    enum class State {
        LOADING,
        DONE
    }

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state
    val progressVisibility: LiveData<Visibility> = _state.map {
        if (it == State.DONE) {
            Visibility.GONE
        } else {
            Visibility.VISIBLE
        }
    }

    fun init() {
        viewModelScope.launch {
            _state.postValue(State.LOADING)
            delay(1500L)
            _state.postValue(State.DONE)
        }
    }
}