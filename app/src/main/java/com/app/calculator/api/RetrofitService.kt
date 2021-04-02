package com.app.calculator.api

import com.app.calculator.data.Admin
import com.app.calculator.data.Schedule
import com.app.calculator.data.ScheduleWrapper
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @GET("/getSchedule")
    fun getSchedule(): Call<ScheduleWrapper>
}