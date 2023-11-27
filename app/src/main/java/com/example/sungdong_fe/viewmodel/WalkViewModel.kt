package com.example.sungdong_fe.viewmodel

import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.R
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.model.db.Glob
import com.skt.Tmap.TMapData
import com.skt.Tmap.TMapPoint
import com.skt.Tmap.TMapPolyLine
import com.skt.Tmap.TMapView
import com.skt.tmap.engine.navigation.route.data.MapPoint
import com.skt.tmap.engine.navigation.route.data.WayPoint
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class WalkViewModel : ViewModel() {
    private val _destination = MutableLiveData<Dto.PlaceLocation>()
    private val _isWalk = MutableLiveData<Boolean>()
    private val _markers = MutableLiveData<List<Dto.Marker>?>()
    private val _tmapview = MutableLiveData<TMapView?>()
    private val _isDetailMap = MutableLiveData<Boolean>()
    val destination : LiveData<Dto.PlaceLocation>
        get() = _destination
    val isWalk : LiveData<Boolean>
        get() = _isWalk

    val markers : LiveData<List<Dto.Marker>?>
        get() = _markers
    val tmapview : LiveData<TMapView?>
        get() = _tmapview
    val state : String
        get() = when(_isWalk.value){
            true -> "산책진행중"
            else -> "시설지도 입장중"
        }
    val isDetailMap : LiveData<Boolean>
        get() = _isDetailMap
    val tagEnabled : Int
        get() = when(_isWalk.value){
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }
    fun updateTmapview(findView: TMapView){
        _tmapview.value = findView
    }
    @RequiresApi(Build.VERSION_CODES.P)
    fun updateDestination(findDestination: Dto.PlaceLocation) {
        // 산책 시작
        _destination.value = findDestination
        try {
            val startPoint = TMapPoint(Glob.userLocation().latitude, Glob.userLocation().longitude)
            val endPoint = TMapPoint(findDestination.ycoordinate.toDouble(), findDestination.xcoordinate.toDouble())

            tmapview.value?.setCenterPoint(startPoint.longitude, startPoint.latitude, false)
            tmapview.value?.setSightVisible(true)
            tmapview.value?.setLocationPoint(startPoint.longitude, startPoint.latitude)
            tmapview.value?.setTrackingMode(true)
            tmapview.value?.setRotateEnable(true)
            tmapview.value?.setCompassMode(true)

            getPath(startPoint, endPoint)
        }catch (e: Exception){
            println(e)
        }
    }
    fun updateIsWalk(findBoolean: Boolean){
        _isWalk.value = findBoolean
    }
    private fun getPath(startPoint: TMapPoint, endPoint: TMapPoint) = try{
        TMapData().apply {
            findPathDataWithType(TMapData.TMapPathType.PEDESTRIAN_PATH, startPoint, endPoint
            ) { polyLine ->
                if (polyLine != null) {
                    polyLine.lineWidth = 15F
                    polyLine.lineColor = Color.Black.hashCode()
                    polyLine.lineAlpha = 50
                    tmapview.value?.addTMapPolyLine("line", polyLine)
                }
            }
        }
    }catch (e: Exception){
        println(e.stackTrace)
    }
    fun updateIsDetailMap(findBoolean: Boolean){
        _isDetailMap.value = findBoolean
    }
    fun updateMarker(findLongitude: Double, findLatitude: Double, findLimit: Int) = CoroutineScope(Dispatchers.Default).launch {
        try{
            val request =
                CoroutineScope(Dispatchers.IO).async { Api.retrofitClient(false).getPlacesMarker(findLongitude.toString(), findLatitude.toString(), findLimit) }
            val response = request.await()
            when (response.code()) {
                200 -> {
                    _markers.postValue(response.body()?.get("markers"))
                }
                else -> {println(response.code())}
            }
        }catch (e: Exception){

        }
    }
    fun arrive() = CoroutineScope(Dispatchers.Default).launch{
        try{
            val request = CoroutineScope(Dispatchers.IO).async { Api.retrofitClient(true).setWalkHistory(destination.value!!.id) }
            val response = request.await()
        }catch (e:Exception){
            println(e)
        }
    }
    init {
        _isWalk.value = false
        _markers.value = listOf()
        _isDetailMap.value = false
    }
}
