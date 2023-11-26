package com.example.sungdong_fe.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.model.db.Glob
import com.skt.tmap.engine.navigation.SDKManager
import com.skt.tmap.engine.navigation.route.RoutePlanType
import com.skt.tmap.engine.navigation.route.data.MapPoint
import com.skt.tmap.engine.navigation.route.data.WayPoint
import com.skt.tmap.vsm.coordinates.VSMCoordinates
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class WalkViewModel : ViewModel() {
    private val _destination = MutableLiveData<Dto.PlaceLocation>()
    private val _isWalk = MutableLiveData<Boolean>()
    private val _markers = MutableLiveData<List<Dto.Marker>?>()

    val destination : LiveData<Dto.PlaceLocation>
        get() = _destination
    val isWalk : LiveData<Boolean>
        get() = _isWalk
    val markers : LiveData<List<Dto.Marker>?>
        get() = _markers
    val state : String
        get() = when(_isWalk.value){
            true -> "산책진행중"
            else -> "성동시설도착"
        }

    val tagEnabled : Int
        get() = when(_isWalk.value){
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }

    fun updateDestination(findDestination: Dto.PlaceLocation) {
        // 산책 시작
        _destination.value = findDestination
        try {
            val currentLocation = Glob.userLocation()
            val currentName = VSMCoordinates.getAddressOffline(currentLocation.longitude, currentLocation.latitude) ?: "현재위치"
            val startPoint = WayPoint(currentName, MapPoint(currentLocation.longitude, currentLocation.latitude))
            val endPoint = WayPoint(findDestination.name, MapPoint(findDestination.xcoordinate.toDouble(), findDestination.ycoordinate.toDouble()))
            val planList = arrayListOf(RoutePlanType.Traffic_Recommend)

            TmapUISDK.getFragment().requestRoute(
                startPoint,
                null,
                endPoint,
                false, object: TmapUISDK.RouteRequestListener{
                    override fun onFail(errorCode: Int, errorMsg: String?) {
                        println(errorCode)
                    }

                    override fun onSuccess() {
                        println("성공")
                    }

                }, planList)
        }catch (e: Exception){
            println(e)
        }
    }
    fun updateIsWalk(findBoolean: Boolean){
        _isWalk.value = findBoolean
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
    init {
        _isWalk.value = false
        _markers.value = listOf()
    }
}
