package com.example.sungdong_fe.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sungdong_fe.R
import com.example.sungdong_fe.databinding.ActivityBottomNavigationBinding
import com.example.sungdong_fe.model.db.Glob.APP_KEY
import com.example.sungdong_fe.view.component.HeaderFragment
import com.example.sungdong_fe.viewmodel.component.HeaderViewModel
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPermission()
        HeaderFragment.viewModel = ViewModelProvider(this).get(HeaderViewModel::class.java)
        transactionFragment(binding.menuFragment.id, HomeFragment())
        transactionFragment(binding.header.id, HeaderFragment())

        binding.nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menu_home -> {
                    transactionFragment(binding.menuFragment.id, HomeFragment())
                    HeaderFragment.viewModel.updateSearchBtnEnabled(View.GONE)
                    binding.gps.visibility = View.GONE
                }
                R.id.menu_walk -> {
                    transactionFragment(binding.menuFragment.id, TmapUISDK.getFragment())
                    HeaderFragment.viewModel.updateSearchBtnEnabled(View.VISIBLE)
                    binding.gps.visibility = View.VISIBLE
                }
                R.id.menu_event -> {
                    transactionFragment(binding.menuFragment.id, EventFragment())
                    HeaderFragment.viewModel.updateSearchBtnEnabled(View.GONE)
                    binding.gps.visibility = View.GONE
                }
            }
            true
        }
        binding.gps.setOnClickListener {
            val (longi, lati) = getCurrentLocation()
            setMapCenterPoint(longi, lati)
        }
        TmapUISDK.initialize(this, "", APP_KEY, "", "", object : TmapUISDK.InitializeListener{
            override fun onFail(errorCode: Int, errorMsg: String?) {
                TODO("Not yet implemented")
            }

            override fun onSuccess() {

            }
        })
    }
    private fun transactionFragment(container_id: Int, fragment: Fragment) = supportFragmentManager
        .beginTransaction()
        .replace(container_id, fragment)
        .commitAllowingStateLoss()


    fun setMapCenterPoint(longi: Double?, lati: Double?){
        if(longi != null && lati != null) {
            TmapUISDK.getFragment().getMapView()
                ?.setMapCenter(longi, lati, true)
        }
    }
    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() : Pair<Double?, Double?>{
        val locationManager = ((this as Context).getSystemService(Context.LOCATION_SERVICE) as LocationManager?)!!
        val locationProvider = LocationManager.GPS_PROVIDER
        val userLocation: Location? = locationManager.getLastKnownLocation(locationProvider)

        return Pair(userLocation?.longitude, userLocation?.latitude)
    }
    private fun getPermission(){
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            val permissions = arrayOf(android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            ActivityCompat.requestPermissions(this, permissions, 100)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.isNotEmpty()) {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED)
                        super.onBackPressed()
                }
            }
        }
    }
}