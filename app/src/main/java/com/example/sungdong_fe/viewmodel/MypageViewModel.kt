package com.example.sungdong_fe.viewmodel

import android.widget.Toast
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
import kotlinx.coroutines.withContext

class MypageViewModel : ViewModel() {
    private val _places = MutableLiveData<List<Dto.Footprint>>()
    private val _isLogin = MutableLiveData<Boolean>()
    val places : LiveData<List<Dto.Footprint>>
        get() = _places

    val isLogin : LiveData<Boolean>
        get() = _isLogin
    fun updateFootprint() = CoroutineScope(Dispatchers.Default).launch {
        try {
            val request =
                CoroutineScope(Dispatchers.IO).async { Api.retrofitClient(true).getUserPlaces(Glob.prefs.getInt("userId",0)) }
            val response = request.await()
            when (response.code()) {
                200 -> {
                    _places.postValue(response.body()?.get("places"))
                }
                else -> {println(response.code())}
            }
        } catch (e: Exception) {

        }
    }
    fun login(id: String, pwd: String) = CoroutineScope(Dispatchers.Default).launch {
        try {
            val request =
                CoroutineScope(Dispatchers.IO).async { Api.retrofitClient(false).getUserLogin(Dto.LoginRequest(id, pwd)) }
            val response = request.await()
            when (response.code()) {
                200 -> {
                    val userId = response.body()?.id
                    if (userId != null) {
                        Glob.prefs.setInt("userId", userId)
                    }
                    val token = response.body()?.token
                    if(token != null){
                        Glob.prefs.setString("token",token)
                    }
                    _isLogin.postValue(true)
                }
                else -> {println(response.code())}
            }
        }catch (e: Exception){

        }
    }
    init {
        _places.value = listOf()
        _isLogin.value = when(Glob.prefs.getInt("userId",0)){
            0 -> false
            else -> true
        }
    }
}