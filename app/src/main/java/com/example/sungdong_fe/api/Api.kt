package com.example.sungdong_fe.api

import com.example.sungdong_fe.model.db.Dto
import com.example.sungdong_fe.model.db.Dto.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {
    companion object{
        fun retrofitClient(isHeader: Boolean) = RetrofitClient.getDefaultClient(isHeader).create(Api::class.java)
    }
    // 근처 장소 리스트 조회
    @GET("/places")
    suspend fun getPlaces(
        @Query("xCoordinate") xCoordinate : String,
        @Query("yCoordinate") yCoordinate : String,
    ): Response<HashMap<String,List<PlaceInfo>>>
    @GET("/places/marker")
    suspend fun getPlacesMarker(
        @Query("xCoordinate") xCoordinate: String,
        @Query("yCoordinate") yCoordinate: String,
        @Query("limit") limit: Int
    ): Response<HashMap<String, List<Marker>>>
    @GET("/places/search")
    suspend fun getPlacesSearch(
        @Query("keyword") keyword : String,
    ): Response<HashMap<String,List<PlaceLocation>>>

    @GET("/events")
    suspend fun getEvents(
        @Query("category") category: String?,
        @Query("place_id") place_id: Int?
    ): Response<HashMap<String,List<Event>>>
    @GET("user/places")
    suspend fun getUserPlaces(
        @Query("userId") userId: Int
    ): Response<HashMap<String, List<Footprint>>>
    @POST("user/login")
    suspend fun getUserLogin(
        @Body request : LoginRequest
    ): Response<User>
    @POST("/routes/pedestrian")
    suspend fun getPath(
        @Query("version") version: String,
        @Query("callback") callback: String?,
        @Body request: PathRequest
    )
}
