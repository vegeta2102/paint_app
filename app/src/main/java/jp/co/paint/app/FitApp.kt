package jp.co.paint.app

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.GsonBuilder
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
        Kotpref.init(this)
        if (Kotpref.gson == null) {
            Kotpref.gson = GsonBuilder().create()
        }
    }
}