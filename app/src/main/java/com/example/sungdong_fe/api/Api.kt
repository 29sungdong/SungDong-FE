package com.example.sungdong_fe.api

import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.model.db.Dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    companion object{
        val retrofitClient = RetrofitClient.getDefaultClient().create(Api::class.java)
    }
    // 근처 장소 리스트 조회
    @GET("/places")
    suspend fun getPlaces(
        @Query("xCoordinate") xCoordinate : String,
        @Query("yCoordinate") yCoordinate : String,
    ): Response<HashMap<String,List<PlaceInfo>>>

    @GET("/places/search")
    suspend fun getPlacesSearch(
        @Query("keyword") keyword : String,
    ): Response<HashMap<String,List<PlaceLocation>>>

    @GET("/events")
    suspend fun getEvents(
        @Query("category") category: String?,
        @Query("place_id") place_id: Int?
    ): Response<HashMap<String,List<Event>>>

}