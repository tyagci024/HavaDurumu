package com.example.havadurumu.model

data class Daily(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: Temp,
    val weather: List<Weather>
)