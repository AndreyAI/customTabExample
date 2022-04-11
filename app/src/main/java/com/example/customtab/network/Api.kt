package com.example.customtab.network

import com.example.customtab.data.FoodDetail
import com.example.customtab.data.WrapFood
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("?s=food")
    suspend fun getFood(
        @Query("page") page: Int
    ): WrapFood

    @GET(".")
    fun getDetailInfo(
        @Query("i") id: String
    ): Call<FoodDetail>
}