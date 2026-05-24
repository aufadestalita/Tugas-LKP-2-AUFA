package com.example.aufapunya.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    // URL dasar (Raw link tanpa nama filenya)
    private const val BASE_URL = "https://gist.githubusercontent.com/aufadestalita/cf40353f4b509980cab98b1ea3746a43/raw/73533b857e87b0e24e15a5ed0760fb097cff9d5f/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}