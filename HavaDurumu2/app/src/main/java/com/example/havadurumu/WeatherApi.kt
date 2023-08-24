package com.example.havadurumu


import com.example.havadurumu.model.OneCallModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    // Base URL sadece "https://api.openweathermap.org/data/2.5/" olmalıdır.
    // Ve `@GET` anotasyonunda endpoint'i belirtmelisiniz.

    @GET("weather")
    fun getData(
        @Query("q") cityName: String,  // q parametresi şehir ismini temsil eder.
        @Query("appid") appId: String, // appid parametresi API anahtarınızı temsil eder.
        @Query("units") units: String = "metric"  // units parametresi sıcaklık birimini belirler. Varsayılan olarak "metric" kullanıldı.
    ): Single<WeatherModel>

    @GET("onecall")
    fun getOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") apiKey: String
    ): Single<OneCallModel>
}