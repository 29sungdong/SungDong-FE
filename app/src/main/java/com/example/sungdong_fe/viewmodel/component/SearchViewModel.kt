package com.example.sungdong_fe.viewmodel.component

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sungdong_fe.api.Api
import com.example.sungdong_fe.model.db.Dto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val _sheetEnabled = MutableLiveData<Int>()
    private val _searchResult = MutableLiveData<List<Dto.PlaceLocation>>()

    // getter
    val sheetEnabled : LiveData<Int>
        get() =  _sheetEnabled
    val searchResult : LiveData<List<Dto.PlaceLocation>>
        get() = _searchResult

    // setter
    fun updateSheetEnabled(){
        _sheetEnabled.value = when(_sheetEnabled.value){
            View.VISIBLE -> {
                _searchResult.value = emptyList()
                View.GONE
            }
            else -> View.VISIBLE
        }
    }
    fun updateSearchResult(searchWord: String)= CoroutineScope(Default).launch {
        if(searchWord.isEmpty()){
            if(searchResult.value?.size!! > 0)
                _searchResult.postValue(emptyList())
        }else {
            try {
                val request =
                    CoroutineScope(IO).async { Api.retrofitClient(false).getPlacesSearch(searchWord) }
                val response = request.await()
                when (response.code()) {
                    200 -> _searchResult.postValue(response.body()?.get("markers"))
                    else -> {}
                }
            } catch (e: Exception) {

            }
        }
    }

    init{
        _sheetEnabled.value = View.GONE
        _searchResult.value = emptyList()
    }
}