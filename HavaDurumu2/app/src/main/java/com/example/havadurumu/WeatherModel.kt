package com.example.havadurumu

import com.example.havadurumu.model.Coord
import com.example.havadurumu.model.Main
import com.example.havadurumu.model.Weather

data class WeatherModel(
    val base: String,
    val cod: Int,
    val dt: Int,
    val id: Int,
    val name: String,
    val timezone: Int,
    val visibility: Int,
    val coord: Coord,
    val main: Main,
    val weather: List<Weather>,
)