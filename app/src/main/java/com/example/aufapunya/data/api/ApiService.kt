package com.example.aufapunya.data.api

import com.example.aufapunya.data.model.InfoJurusan
import retrofit2.http.GET

interface ApiService {
    @GET("data_jurusan.json")
    suspend fun getJurusan(): List<InfoJurusan>
}