package com.example.sungdong_fe.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.view.component.SearchFragment
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK


class WalkViewModel : ViewModel() {
    private val _destination = MutableLiveData<Dto.PlaceLocation>()
    private val _isWalk = MutableLiveData<Boolean>()
    private val _state = MutableLiveData<String>()
    private val _markers = MutableLiveData<Dto.PlaceLocation>()

    val destination : LiveData<Dto.PlaceLocation>
        get() = _destination
    val isWalk : LiveData<Boolean>
        get() = _isWalk

    val state : String
        get() = when(_isWalk.value){
            true -> "산책진행중"
            else -> "성동시설도착"
        }

    val tagEnabled : Int
        get() = when(_isWalk.value){
            true -> View.VISIBLE
            else -> View.INVISIBLE
        }

    fun updateDestination(findDestination: Dto.PlaceLocation) {
        // 산책 시작
        _destination.value = findDestination
//        SearchFragment.viewModel.updateSheetEnabled()
//        TmapUISDK.getFragment().startSafeDrive()
    }
    fun updateIsWalk(findBoolean: Boolean){
        _isWalk.value = findBoolean
    }
    init {
        _isWalk.value = false
    }
}
