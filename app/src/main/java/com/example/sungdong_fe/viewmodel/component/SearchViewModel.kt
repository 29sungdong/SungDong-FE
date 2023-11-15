package com.example.sungdong_fe.viewmodel.component

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _sheetEnabled = MutableLiveData<Int>()
    private val _searchResult = MutableLiveData<Array<String>>()

    // getter
    val sheetEnabled : LiveData<Int>
        get() =  _sheetEnabled
    val searchResult : LiveData<Array<String>>
        get() = _searchResult

    // setter
    fun updateEnabled(){
        _sheetEnabled.value = when(_sheetEnabled.value){
            View.VISIBLE -> {
                _searchResult.value = emptyArray()
                View.GONE
            }
            else -> View.VISIBLE
        }
    }
    fun updateSearchResult(searchWord: String){
        // response array
        val findArray = arrayOf(searchWord)
        _searchResult.value = findArray
    }

    init{
        _sheetEnabled.value = View.GONE
        _searchResult.value = emptyArray()
    }
}