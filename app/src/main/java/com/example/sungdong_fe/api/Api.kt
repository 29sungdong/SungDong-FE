package com.example.sungdong_fe.api

import android.media.Image
import com.example.sungdong_fe.model.db.Dto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    companion object{
        val retrofitClient = RetrofitClient.getDefaultClient().create(Api::class.java)
        val TmapOpenApi = RetrofitClient.getTmapClient().create(Api::class.java)
    }
    // 근처 장소 리스트 조회
    @GET("/places/{xCoordinate}/{yCoordinate}")
    suspend fun getPlaces(
        @Path("xCoordinate") xCoordinate : String,
        @Path("yCoordinate") yCoordinate : String,
    ): Response<List<Dto.Place>>
}