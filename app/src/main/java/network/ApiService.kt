package com.example.aufapunya.network

import com.example.aufapunya.model.InfoJurusan
import retrofit2.http.GET

interface ApiService {
    @GET("data_jurusan.json")
    suspend fun getJurusan(): List<InfoJurusan>
}