package com.example.sungdong_fe.model.db

import com.skt.tmap.engine.navigation.SDKManager
import com.skt.tmap.vsm.coordinates.VSMCoordinates




object Glob {
    val APP_KEY = "qT7dD5vUfW5vDWypqKwBO74qHiihiEi8126GHvsr"
    fun userLocation() = SDKManager.getInstance().getCurrentPosition()
    var currentLocationName = VSMCoordinates.getAddressOffline(
        userLocation().longitude,
        userLocation().latitude
    )
}