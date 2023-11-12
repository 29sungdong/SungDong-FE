package com.example.sungdong_fe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeaderViewModel : ViewModel() {
    private val _searchText = MutableLiveData<String>()

    // getter
    val searchText : LiveData<String>
        get() = _searchText

    // setter
    fun updateSearchText(findStr: String){
        _searchText.value = findStr
    }

    init{
        _searchText.value = ""
    }



}