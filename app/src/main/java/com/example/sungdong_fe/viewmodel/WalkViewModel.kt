package com.example.sungdong_fe.viewmodel


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class WalkViewModel : ViewModel() {
    private val _longitude = MutableLiveData<Double>()
    private val _latitude = MutableLiveData<Double>()

    // getter
    val longitude : LiveData<Double>
        get() = _longitude
    val latitude : LiveData<Double>
        get() = _latitude

    // setter
    @SuppressLint("MissingPermission")
    fun updateLocation(context: Context?){
        val locationManager = (context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?)!!
        val locationProvider = LocationManager.GPS_PROVIDER
        val userLocation: Location? = locationManager.getLastKnownLocation(locationProvider)

        if(userLocation != null){
            _longitude.value = userLocation.longitude
            _latitude.value = userLocation.latitude
        }
    }
}