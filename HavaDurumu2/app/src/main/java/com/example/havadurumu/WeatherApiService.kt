package com.example.havadurumu

import com.example.havadurumu.model.OneCallModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class WeatherApiService {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private val API_KEY = "04a42b96398abc8e4183798ed22f9485"  // API anahtarınızı buraya yazın

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherApi::class.java)

    fun getDataService(cityName: String): Single<WeatherModel> {
        return api.getData(cityName, API_KEY)
    }
    fun getOneCallService(lat: Double, lon: Double): Single<OneCallModel> {
        return api.getOneCall(lat, lon, "current,minutely,hourly,alerts", API_KEY)
    }
}