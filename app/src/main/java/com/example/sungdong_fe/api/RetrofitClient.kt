package com.example.sungdong_fe.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://13.125.49.125"
    private const val TMAP_BASE_URL = "https://apis.openapi.sk.com/tmap"
    fun getDefaultClient() : Retrofit {
        return Retrofit.Builder()
            //서버 url설정
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // 객체정보 반환
            .build()
    }
    fun getTmapClient() : Retrofit {
        return Retrofit.Builder()
            //서버 url설정
            .baseUrl(TMAP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // 객체정보 반환
            .build()
    }
}