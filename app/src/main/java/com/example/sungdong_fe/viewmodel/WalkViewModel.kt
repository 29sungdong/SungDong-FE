package com.example.sungdong_fe.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.view.component.SearchFragment
import com.tmapmobility.tmap.tmapsdk.ui.util.TmapUISDK


class WalkViewModel : ViewModel() {
    private val _destination = MutableLiveData<Dto.PlaceLocation>()
    private val _markers = MutableLiveData<Dto.PlaceLocation>()

    val destination : LiveData<Dto.PlaceLocation>
        get() = _destination

    fun updateDestination(findDestination: Dto.PlaceLocation) {
        // 산책 시작
        _destination.value = findDestination
        SearchFragment.viewModel.updateSheetEnabled()
        TmapUISDK.getFragment().startSafeDrive()
    }
}
