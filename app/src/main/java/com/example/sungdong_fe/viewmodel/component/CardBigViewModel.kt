package com.example.sungdong_fe.viewmodel.component

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.model.db.Glob
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CardBigViewModel : ViewModel() {
    private val _places = MutableLiveData<List<Dto.PlaceInfo>?>()

    val places : LiveData<List<Dto.PlaceInfo>?>
        get() = _places

    fun updatePlaces()= CoroutineScope(Dispatchers.Default).launch {
        try{
            val request =
                CoroutineScope(Dispatchers.IO).async { Api.retrofitClient.getPlaces(Glob.userLocation().longitude.toString(), Glob.userLocation().latitude.toString()) }
            val response = request.await()
            when (response.code()) {
                200 -> {
                    _places.postValue(response.body()?.get("places"))
                }
                else -> {println(response.code())}
            }
        }catch (e: Exception){

        }
    }

    init {
        _places.value = listOf()
    }
}