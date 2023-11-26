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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.location.component1
import androidx.core.location.component2
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sungdong_fe.R
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.databinding.ActivityBottomNavigationBinding
import com.example.sungdong_fe.model.db.Glob
import com.example.sungdong_fe.model.db.Glob.Companion.APP_KEY
import com.example.sungdong_fe.model.db.Glob.Companion.userLocation
import com.example.sungdong_fe.view.component.HeaderFragment
import com.example.sungdong_fe.view.component.SearchFragment
import com.example.sungdong_fe.viewmodel.WalkViewModel
import com.example.sungdong_fe.viewmodel.component.HeaderViewModel
import com.example.sungdong_fe.viewmodel.component.SearchViewModel
import com.skt.tmap.engine.navigation.SDKManager
import com.skt.tmap.vsm.data.VSMMapPoint
import com.skt.tmap.vsm.map.MapConstant
import com.skt.tmap.vsm.map.MapEngine.OnHitCalloutPopupListener
import com.skt.tmap.vsm.map.MapEngine.OnHitObjectListener
import com.skt.tmap.vsm.map.marker.MarkerImage
import com.skt.tmap.vsm.map.marker.VSMMarkerBase
import com.skt.tmap.vsm.map.marker.VSMMarkerPoint
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK
import com.tmapmobility.tmap.tmapsdk.ui.view.MapConstant.MarkerRenderingPriority.DEFAULT_PRIORITY
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
        WalkFragment.viewModel.isWalk.observe(this) {
            binding.tag.visibility = WalkFragment.viewModel.tagEnabled
            when (it) {
                true -> {
                    transactionFragment(binding.menuFragment.id, WalkFragment())
                }

                else -> {
                    transactionFragment(binding.menuFragment.id, TmapUISDK.getFragment())
                    binding.nav.selectedItemId = R.id.menu_walk
                    binding.nav.isClickable = true
                }
            }
        }
        HeaderFragment.viewModel.searchBtnEnabled.observe(this) {
            binding.gps.visibility = it
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
                    marker.showPriority = DEFAULT_PRIORITY.toFloat()
                    marker.text = m.name
                    markerManager?.addMarker(marker)
                }
            }
        }
        TmapUISDK.getFragment().setHitEventListener(object: OnHitObjectListener{
            override fun OnHitObjectPOI(
                p0: String?,
                p1: Int,
                p2: VSMMapPoint?,
                p3: Bundle?
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectMarker(p0: VSMMarkerBase?, p1: Bundle?): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectOilInfo(p0: String?, p1: Int, p2: VSMMapPoint?): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectTraffic(
                p0: String?,
                p1: Int,
                p2: String?,
                p3: String?,
                p4: String?,
                p5: VSMMapPoint?
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectCctv(
                p0: String?,
                p1: Int,
                p2: VSMMapPoint?,
                p3: Bundle?
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectAlternativeRoute(p0: String?, p1: VSMMapPoint?): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectRouteFlag(p0: String?, p1: Int, p2: VSMMapPoint?): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectRouteLine(p0: String?, p1: Int, p2: VSMMapPoint?): Boolean {
                TODO("Not yet implemented")
            }

            override fun OnHitObjectNone(p0: VSMMapPoint?): Boolean {
                TODO("Not yet implemented")
            }

        }, object: OnHitCalloutPopupListener{
            override fun OnHitCalloutPopupPOI(p0: String?, p1: Int, p2: VSMMapPoint?, p3: Bundle?) {
                TODO("Not yet implemented")
            }

            override fun OnHitCalloutPopupMarker(p0: VSMMarkerBase?) {
                TODO("Not yet implemented")
            }

            override fun OnHitCalloutPopupTraffic(
                p0: String?,
                p1: Int,
                p2: String?,
                p3: String?,
                p4: String?,
                p5: VSMMapPoint?
            ) {
                TODO("Not yet implemented")
            }

            override fun OnHitCalloutPopupCctv(
                p0: String?,
                p1: Int,
                p2: VSMMapPoint?,
                p3: Bundle?
            ) {
                TODO("Not yet implemented")
            }

            override fun OnHitCalloutPopupUserDefine(p0: String?, p1: Int, p2: VSMMapPoint?) {
                TODO("Not yet implemented")
            }

        })
        TmapUISDK.initialize(this, "", APP_KEY, "", "", object : TmapUISDK.InitializeListener {
            override fun onFail(errorCode: Int, errorMsg: String?) {
                TODO("Not yet implemented")
            }

            override fun onSuccess() {
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