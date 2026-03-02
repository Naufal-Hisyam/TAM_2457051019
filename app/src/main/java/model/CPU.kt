package com.example.praktam_2457051019.model

import androidx.annotation.DrawableRes

data class CPU(
    val nama: String,
    val deskripsi: String,
    @DrawableRes val imageRes: Int
)