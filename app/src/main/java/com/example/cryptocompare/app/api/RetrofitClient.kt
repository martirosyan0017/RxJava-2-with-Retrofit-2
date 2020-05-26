package com.example.cryptocompare.app.api

import com.example.cryptocompare.app.constants.Network
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient private constructor() {

    companion object {
        private val BASE_URL = Network.BASE_URL

        @Volatile
        private var mInstance: RetrofitClient? = null
        @get:Synchronized
        val instance: RetrofitClient
            get() {
                if (mInstance == null) {
                    mInstance = RetrofitClient()
                }
                return mInstance!!
            }
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    val apiService :ApiService = retrofit.create(ApiService::class.java)
}
