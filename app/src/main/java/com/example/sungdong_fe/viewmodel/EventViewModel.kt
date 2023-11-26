package com.example.sungdong_fe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.R
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.model.db.Category
import com.example.sungdong_fe.model.db.Dto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EventViewModel : ViewModel() {
    private val _currentCategory = MutableLiveData<Category>()
    private val _events = MutableLiveData<List<Dto.Event>>()
    private val _isLinkOpened = MutableLiveData<Boolean>()
    private val _eventUrl = MutableLiveData<String>()
    val currentCategory : LiveData<Category>
        get() = _currentCategory
    val events : LiveData<List<Dto.Event>>
        get() = _events

    val isLinkOpened : LiveData<Boolean>
        get() = _isLinkOpened

    val eventUrl : LiveData<String>
        get() = _eventUrl

    fun updateCategory(findCategory: Category){
        _currentCategory.value = findCategory
    }
    fun updateEvents(searchCategory: String?, place_id: Int?)= CoroutineScope(Dispatchers.Default).launch {
        try {
            val request =
                CoroutineScope(Dispatchers.IO).async { Api.retrofitClient(false).getEvents(searchCategory, place_id) }
            val response = request.await()
            when (response.code()) {
                200 -> {
                    _events.postValue(response.body()?.get("events"))
                }
                404 -> {
                    _events.postValue(listOf())
                }
                else -> {println(response.code())}
            }
        } catch (e: Exception) {

        }
    }
    fun updateIsLinkOpened(findBoolean: Boolean, findUrl: String?){
        _isLinkOpened.value = findBoolean
        if(findUrl != null) {
            _eventUrl.value = findUrl
        }
    }
    init {
        _currentCategory.value = Category.ALL
        _events.value = listOf()
    }
}