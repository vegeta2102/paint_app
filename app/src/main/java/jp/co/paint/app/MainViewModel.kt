package jp.co.paint.app

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import jp.co.paint.dialog.DialogMessageRepository
import kotlinx.coroutines.flow.filterNotNull

/**
 * Created by vegeta
 */
class MainViewModel @ViewModelInject constructor(
    private val dialogMessageRepository: DialogMessageRepository
) : ViewModel() {

    val requestDialogMessage =
        dialogMessageRepository.dialogMessageFlow.filterNotNull().asLiveData()

}