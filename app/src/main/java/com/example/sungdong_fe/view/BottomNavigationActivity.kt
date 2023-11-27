package com.example.sungdong_fe.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.location.component1
import androidx.core.location.component2
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sungdong_fe.R
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.databinding.ActivityBottomNavigationBinding
import com.example.sungdong_fe.model.db.Glob
import com.example.sungdong_fe.model.db.Glob.Companion.APP_KEY
import com.example.sungdong_fe.model.db.Glob.Companion.userLocation
import com.example.sungdong_fe.view.component.DetailMapFragment
import com.example.sungdong_fe.view.component.HeaderFragment
import com.example.sungdong_fe.view.component.SearchFragment
import com.example.sungdong_fe.viewmodel.WalkViewModel
import com.example.sungdong_fe.viewmodel.component.HeaderViewModel
import com.example.sungdong_fe.viewmodel.component.SearchViewModel
import com.skt.Tmap.TMapView
import com.skt.Tmap.TMapPoint
import com.skt.tmap.engine.navigation.SDKManager
import com.skt.tmap.navirenderer.MarkerClick
import com.skt.tmap.vsm.data.VSMMapPoint
import com.skt.tmap.vsm.map.MapConstant
import com.skt.tmap.vsm.map.MapEngine
import com.skt.tmap.vsm.map.MapEngine.OnHitCalloutPopupListener
import com.skt.tmap.vsm.map.MapEngine.OnHitObjectListener
import com.skt.tmap.vsm.map.marker.MarkerImage
import com.skt.tmap.vsm.map.marker.VSMMarkerBase
import com.skt.tmap.vsm.map.marker.VSMMarkerPoint
import com.tmapmobility.tmap.tmapsdk.ui.data.MapSetting
import com.tmapmobility.tmap.tmapsdk.ui.data.NightMode
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK
import com.tmapmobility.tmap.tmapsdk.ui.view.MapConstant.MarkerRenderingPriority.DEFAULT_PRIORITY
import com.tmapmobility.tmap.tmapsdk.ui.view.MapConstant.MarkerRenderingPriority.SELECT_MARKER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBottomNavigationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPermission()
        // component 연결
        HeaderFragment.viewModel = ViewModelProvider(this).get(HeaderViewModel::class.java)
        SearchFragment.viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        WalkFragment.viewModel = ViewModelProvider(this).get(WalkViewModel::class.java)

        transactionFragment(binding.header.id, HeaderFragment())
        transactionFragment(binding.sheet.id, SearchFragment())
        transactionFragment(binding.walk.id, WalkFragment())

        val tmapView = TMapView(this@BottomNavigationActivity)
        tmapView.setSKTMapApiKey(APP_KEY)
        WalkFragment.viewModel.updateTmapview(tmapView)

        binding.nav.setOnItemSelectedListener {
            if (HeaderFragment.viewModel.isMypageSelected.value!!) {
                HeaderFragment.viewModel.updateIsMypageSelected(false)
            }
            when (it.itemId) {
                R.id.menu_home -> {
                    transactionFragment(binding.menuFragment.id, HomeFragment())
                    HeaderFragment.viewModel.updateSearchBtnEnabled(View.GONE)
                }

                R.id.menu_walk -> {
                    when (WalkFragment.viewModel.isWalk.value) {
                        true -> {
                            transactionFragment(binding.menuFragment.id, WalkFragment())
                        }

                        else -> {
                            transactionFragment(binding.menuFragment.id, TmapUISDK.getFragment())
                            HeaderFragment.viewModel.updateSearchBtnEnabled(View.VISIBLE)
                        }
                    }
                }

                R.id.menu_event -> {
                    transactionFragment(binding.menuFragment.id, EventFragment())
                    HeaderFragment.viewModel.updateSearchBtnEnabled(View.GONE)
                }
            }
            true
        }
        binding.gps.setOnClickListener {
            TmapUISDK.getFragment().getMapView()
                ?.setMapCenter(userLocation().longitude, userLocation().latitude, true)
        }
        binding.changeModeBtn.setOnClickListener{
            when(binding.changeModeBtn.text){
                getString(R.string.change_to_walk) -> {
                    binding.changeModeBtn.setText(R.string.change_to_default)
                    TmapUISDK.getFragment().startSafeDrive()
                    binding.gps.visibility = View.GONE
                }
                else -> {
                    binding.changeModeBtn.setText(R.string.change_to_walk)
                    TmapUISDK.getFragment().stopDrive()
                    binding.gps.visibility = View.VISIBLE
                }
            }
        }
        WalkFragment.viewModel.isWalk.observe(this) {
            when (it) {
                true -> {
                    binding.walk.visibility = View.VISIBLE
                    binding.nav.visibility = View.GONE
                }

                else -> {
                    binding.walk.visibility = View.GONE
                    transactionFragment(binding.menuFragment.id, TmapUISDK.getFragment())
                    binding.nav.selectedItemId = R.id.menu_walk
                    binding.nav.visibility = View.VISIBLE
                }
            }
        }
        WalkFragment.viewModel.isDetailMap.observe(this){
            if(it) {
                transactionFragment(
                    binding.menuFragment.id,
                    DetailMapFragment(WalkFragment.viewModel.destination.value!!.id)
                )
                binding.gps.visibility = View.GONE
            }
            else{

            }
        }
        HeaderFragment.viewModel.searchBtnEnabled.observe(this) {
            if(WalkFragment.viewModel.isWalk.value == false) {
                binding.gps.visibility = it
                binding.changeModeBtn.visibility = it
            }
            if(it == View.VISIBLE){
                WalkFragment.viewModel.updateMarker(userLocation().longitude, userLocation().latitude,100)
            }
        }
        HeaderFragment.viewModel.isMypageSelected.observe(this) {
            if (it) {
                transactionFragment(binding.menuFragment.id, MypageFragment())
            }
        }
        WalkFragment.viewModel.markers.observe(this){
            val markerManager = TmapUISDK.getFragment().getMapView()?.markerManager
            val icon = ContextCompat.getDrawable(this, R.drawable.ic_marker)?.toBitmap()
            val icon_tag = ContextCompat.getDrawable(this, R.drawable.ic_marker_tag)?.toBitmap()

            if (it != null) {
                for(m in it) {
                    val marker = VSMMarkerPoint(m.id.toString())
                    val position = VSMMapPoint(m.xcoordinate.toDouble()+0.0001, m.ycoordinate.toDouble() + 0.00015)
                    val position_tag = VSMMapPoint(m.xcoordinate.toDouble()+0.0005, m.ycoordinate.toDouble() + 0.0002)
                    marker.position = when(m.hasEvent){
                        true -> position_tag
                        else -> position
                    }
                    marker.icon = when(m.hasEvent) {
                        true -> {
                            marker.setIconSize(48F, 35F)
                            MarkerImage.fromBitmap(icon_tag!!)
                        }
                        else -> {
                            marker.setIconSize(24F, 30F)
                            MarkerImage.fromBitmap(icon!!)
                        }
                    }
                    marker.animationEnabled = true
                    marker.showPriority = SELECT_MARKER.toFloat()
                    marker.text = m.name
                    markerManager?.addMarker(marker)
                }
            }
        }
        initializeTmapView()
    }
    private fun initializeTmapView(){
        TmapUISDK.initialize(this, "", APP_KEY, "", "", object : TmapUISDK.InitializeListener {
            override fun onFail(errorCode: Int, errorMsg: String?) {
                TODO("Not yet implemented")
            }

            override fun onSuccess() {
                val mapSetting = MapSetting()
                mapSetting.isShowClosedPopup = false
                mapSetting.isUseNightMode = NightMode.ALWAYS_OFF
                TmapUISDK.getFragment().setSettings(mapSetting)
                TmapUISDK.getFragment().getMapView()
                    ?.setMapCenter(userLocation().longitude, userLocation().latitude, true)
                WalkFragment.viewModel.updateMarker(userLocation().longitude, userLocation().latitude,100)
            }
        })
    }
    private fun transactionFragment(container_id: Int, fragment: Fragment) = supportFragmentManager
        .beginTransaction()
        .replace(container_id, fragment)
        .commitAllowingStateLoss()

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