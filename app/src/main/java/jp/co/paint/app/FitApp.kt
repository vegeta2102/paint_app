package jp.co.paint.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by vegeta
 */
@HiltAndroidApp
class FitApp : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    private fun init() {
        // TODO Init here
    }
}