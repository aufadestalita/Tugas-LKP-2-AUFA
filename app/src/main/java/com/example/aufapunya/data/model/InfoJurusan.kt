package com.example.aufapunya.data.model

import com.google.gson.annotations.SerializedName

data class InfoJurusan(
    @SerializedName("namaJurusan") val namaJurusan: String,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("image_url") val imageUrl: String
)