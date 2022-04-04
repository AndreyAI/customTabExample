package com.hammersys.customtab.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {

    private val okhttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            CustomHeaderInterceptor()
        )
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okhttpClient)
        .build()


    val api: Api
        get() = retrofit.create()
}