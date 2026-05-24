package com.example.aufapunya.data.repository

import com.example.aufapunya.data.api.RetrofitClient
import com.example.aufapunya.data.model.InfoJurusan

class JurusanRepository {

    suspend fun getJurusan(): List<InfoJurusan> {
        return try {

            RetrofitClient.instance.getJurusan()
        } catch (e: Exception) {

            emptyList()
        }
    }
}