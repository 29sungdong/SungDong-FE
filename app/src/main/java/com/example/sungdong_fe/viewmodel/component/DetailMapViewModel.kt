package com.example.sungdong_fe.viewmodel.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.model.db.Dto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailMapViewModel : ViewModel() {
    val _subList = MutableLiveData<List<Dto.SubPlace>?>()
    val _subPlace = MutableLiveData<Dto.SubPlace?>()
    val _missions = MutableLiveData<List<Dto.Mission>?>()
    val subList : LiveData<List<Dto.SubPlace>?>
        get() = _subList
    val subPlace : LiveData<Dto.SubPlace?>
        get() = _subPlace
    val missions : LiveData<List<Dto.Mission>?>
        get() = _missions
    fun updateSubList(placeId: Int) = CoroutineScope(Dispatchers.Default).launch{
        try{
            val request = CoroutineScope(Dispatchers.Default).async { Api.retrofitClient(false).getSubPlaces(placeId) }
            val response = request.await()
            when(response.code()){
                200 -> {
                    _subList.postValue(response.body()?.get("subPlaces"))
                }
            }
        }catch (e: Exception){

        }
    }
    fun updateSubPlace(findPlace: Dto.SubPlace){
        updateMissions(findPlace)
        _subPlace.value = findPlace
    }
    private fun updateMissions(subPlace: Dto.SubPlace) = CoroutineScope(Dispatchers.Default).launch{
        try{
            val request = CoroutineScope(Dispatchers.Default).async { Api.retrofitClient(true).getSubPlaceMission(subPlace.id) }
            val response = request.await()
            when(response.code()){
                200 -> {
                    _missions.postValue(response.body()?.get("missions"))
                }
                else->{
                    println(response.code())
                }
            }
        }catch (e: Exception){

        }
    }
    init {
        _subList.value = listOf()
        _subPlace.value = null
        _missions.value = listOf()
    }
}