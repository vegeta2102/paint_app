package jp.co.paint

import jp.co.paint.model.ScreenSize

/**
 * Created by vegeta on 2021/07/19.
 */
interface DisplayInfoRepository {
    val latestData: ScreenSize?
    suspend fun emit(value: ScreenSize)
}