package com.example.aufapunya.model
import androidx.annotation.DrawableRes

data class InfoJurusan(
    val namaJurusan: String,
    val deskripsi: String,
    val prospekKerja: String,
    @DrawableRes val imageRes: Int
)
