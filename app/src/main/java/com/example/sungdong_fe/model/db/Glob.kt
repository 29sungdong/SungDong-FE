package com.example.sungdong_fe.model.db

import android.app.Application
import com.skt.tmap.engine.navigation.SDKManager
import com.skt.tmap.vsm.coordinates.VSMCoordinates




class Glob : Application() {
    companion object {
        val APP_KEY = "qT7dD5vUfW5vDWypqKwBO74qHiihiEi8126GHvsr"
        lateinit var prefs: PreferenceUtil
        fun userLocation() = SDKManager.getInstance().getCurrentPosition()
        var currentLocationName = VSMCoordinates.getAddressOffline(
            userLocation().longitude,
            userLocation().latitude
        )
    }
    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
        prefs.setString("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI3NyIsInR5cGUiOiJhY2Nlc3MiLCJpYXQiOjE3MDEwNjQ1NzAsImV4cCI6MTcwMTE1MDk3MH0.r66G5gPbSXJXrwQNGM-ZrpCZyXfTN76DU-Om-rAWkcLhT_baUsJSOiqJonkV_rzqGhJoloyf0G3uc0a4b3GKQA")
    }
}