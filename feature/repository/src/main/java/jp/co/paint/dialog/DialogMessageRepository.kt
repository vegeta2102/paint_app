package jp.co.paint.dialog

import kotlinx.coroutines.flow.Flow

/**
 * Created by vegeta
 */
interface DialogMessageRepository {
    val dialogMessageFlow: Flow<String?>
    suspend fun emit(
        content: String
    )
}