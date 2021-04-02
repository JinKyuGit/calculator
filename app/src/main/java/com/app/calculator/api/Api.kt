package com.app.calculator.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Api {

    val apiServer = "https://api-hitya.com"

    fun getService(): RetrofitService {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiServer)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(RetrofitService::class.java)
    }



}