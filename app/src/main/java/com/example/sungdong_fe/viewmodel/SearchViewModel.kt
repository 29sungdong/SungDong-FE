package com.example.sungdong_fe.viewmodel

import android.support.v4.os.IResultReceiver.Default
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.api.Api
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : ViewModel() {

    private val _enabled = MutableLiveData<Boolean>()
    private val _searchResult = MutableLiveData<Array<String>>()

    // getter
    val enabled : LiveData<Boolean>
        get() =  _enabled
    val searchResult : LiveData<Array<String>>
        get() = _searchResult

    // setter
    fun updateEnabled(){
        _enabled.value = when(_enabled.value){
            true -> {
                _searchResult.value = emptyArray()
                false
            }
            else -> true
        }
    }
    fun updateSearchResult(searchWord: String){
        // response array
        val findArray = arrayOf(searchWord)
        _searchResult.value = findArray
    }

    init{
        _enabled.value = false
        _searchResult.value = emptyArray()
    }
}