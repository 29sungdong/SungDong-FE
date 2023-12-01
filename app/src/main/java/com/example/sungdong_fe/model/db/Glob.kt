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
    }
}