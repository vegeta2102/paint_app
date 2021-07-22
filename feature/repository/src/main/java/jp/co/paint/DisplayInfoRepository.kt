package jp.co.paint

import jp.co.paint.model.ScreenSize

/**
 * Created by vegeta
 */
interface DisplayInfoRepository {
    val latestData: ScreenSize?
    suspend fun emit(value: ScreenSize)
}