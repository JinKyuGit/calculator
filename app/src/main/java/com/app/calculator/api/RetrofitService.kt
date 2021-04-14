package com.app.calculator.api

import com.app.calculator.data.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    //일정 조회
    @GET("/getScheduleNew")
    fun getSchedule(): Call<ScheduleWrapper>

    //아레나 공덱 조회.
    @POST("/searchArena")
    fun searchArenaInfo(@Body param : ArenaInfo): Call<ArenaInfoWrapper>
    
    //아레나 등록
    @POST("/registerArena")
    fun registerArena(@Body param : ArenaInfo): Call<ArenaInfoWrapper2>
}