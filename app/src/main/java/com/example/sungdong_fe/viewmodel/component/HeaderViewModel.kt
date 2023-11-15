package com.example.sungdong_fe.viewmodel.component

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeaderViewModel : ViewModel() {
    private val _searchBtnEnabled = MutableLiveData<Int>()

    val searchBtnEnabled : LiveData<Int>
        get() = _searchBtnEnabled

    // setter
    fun updateSearchBtnEnabled(findVisibility: Int){
        _searchBtnEnabled.value = findVisibility
    }

    init{
        _searchBtnEnabled.value = View.GONE
    }



}