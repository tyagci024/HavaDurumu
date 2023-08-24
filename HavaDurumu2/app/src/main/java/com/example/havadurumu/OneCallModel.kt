package com.example.havadurumu.model

data class OneCallModel(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int,
    val daily: List<Daily>
)