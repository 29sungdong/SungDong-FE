package com.example.sungdong_fe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.databinding.TopBarFragmentBinding

class TopBarViewModel : ViewModel() {
    private val _searchText = MutableLiveData<String>()

    val searchText : LiveData<String>
        get() = _searchText

    fun updateSearchText(findStr: String){
        _searchText.value = findStr
        println(findStr)
    }

    init{
        _searchText.value = ""
    }


}