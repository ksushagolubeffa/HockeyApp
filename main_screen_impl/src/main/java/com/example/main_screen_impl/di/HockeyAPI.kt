package com.example.main_screen_impl.di

import retrofit2.http.GET
import retrofit2.http.Query

interface HockeyAPI {

    @GET("schedule?expand=schedule.linescore")
    suspend fun getByDate(
        @Query("date") date: String = "2023-02-26"
    ): ResponseModel
}