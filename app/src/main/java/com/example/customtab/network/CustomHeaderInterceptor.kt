package com.hammersys.customtab.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val modifiedUrl = originalRequest.url.newBuilder()//newBuilder()
            .addQueryParameter("apikey", API_KEY)
            .build()

        val modifiedRequest = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }

    companion object {
        private const val API_KEY = "5f9edee6"
    }
}