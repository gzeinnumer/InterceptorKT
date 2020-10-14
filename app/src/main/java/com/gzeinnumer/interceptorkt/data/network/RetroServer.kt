package com.gzeinnumer.interceptorkt.data.network

import com.gzeinnumer.interceptorkt.utils.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroServer {
    private const val base_url = "http://192.168.0.121/retrofit/"
    private fun setInit(): Retrofit {
        val client: OkHttpClient = TokenInterceptor().client
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val instance: ApiService
        get() = setInit().create(ApiService::class.java)
}