package jp.co.paint

import jp.co.paint.model.ScreenSize
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/**
 * Created by vegeta on 2021/07/19.
 */
class DisplayInfoRepositoryImpl @Inject constructor() : DisplayInfoRepository {
    private val data = MutableStateFlow<ScreenSize?>(null)
    override val latestData: ScreenSize?
        get() = data.value

    override suspend fun emit(value: ScreenSize) {
        data.emit(value)
    }
}
