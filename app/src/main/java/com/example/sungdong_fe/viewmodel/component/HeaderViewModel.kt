package com.example.sungdong_fe.viewmodel.component

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeaderViewModel : ViewModel() {
    private val _searchBtnEnabled = MutableLiveData<Int>()
    private val _isMypageSelected = MutableLiveData<Boolean>()

    val searchBtnEnabled : LiveData<Int>
        get() = _searchBtnEnabled

    val isMypageSelected : LiveData<Boolean>
        get() = _isMypageSelected

    // setter
    fun updateSearchBtnEnabled(findVisibility: Int){
        _searchBtnEnabled.value = findVisibility
    }
    fun updateIsMypageSelected(findBoolean: Boolean){
        _isMypageSelected.value = findBoolean
        if(findBoolean){
            _searchBtnEnabled.value = View.GONE
        }
    }

    init{
        _searchBtnEnabled.value = View.GONE
        _isMypageSelected.value = false
    }



}