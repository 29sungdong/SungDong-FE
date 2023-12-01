package com.example.sungdong_fe.api

import com.example.sungdong_fe.model.db.Glob
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://13.125.49.125"
    private const val TMAP_BASE_URL = "https://apis.openapi.sk.com/tmap"
    fun getDefaultClient(isHeader: Boolean) : Retrofit = when(isHeader){
        true -> Retrofit.Builder()
            //서버 url설정
            .baseUrl(BASE_URL)
            // Header 붙이기
            .client(getOkHttpClient(AppInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            // 객체정보 반환
            .build()
        else -> Retrofit.Builder()
            //서버 url설정
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // 객체정보 반환
            .build()
    }
    private fun getOkHttpClient(interceptor: AppInterceptor) : OkHttpClient
            = OkHttpClient.Builder().run{
        addInterceptor(interceptor)
        connectTimeout(300, TimeUnit.SECONDS); // connect timeout
        readTimeout(300, TimeUnit.SECONDS);    // socket timeout
        build()
    }
    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer "+ Glob.prefs.getString("token","logout"))
                .build()
            proceed(newRequest)
        }
    }
}