package com.example.havadurumu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.havadurumu.model.OneCallModel

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class WeatherViewModel: ViewModel() {
    private val weatherApiService = WeatherApiService()
    private val disposable = CompositeDisposable()

    val oneCall_data = MutableLiveData<OneCallModel>()
    val weather_data = MutableLiveData<WeatherModel>()
    val weather_error = MutableLiveData<Boolean>()
    val weather_load = MutableLiveData<Boolean>()

    fun refreshData(cityName: String) {
        weather_load.value = true
        disposable.add(
            weatherApiService.getDataService(cityName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { weatherModel : WeatherModel ->
                    weather_data.value = weatherModel
                    weatherApiService.getOneCallService(weatherModel.coord.lat, weatherModel.coord.lon)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                }
                .subscribeWith(object : DisposableSingleObserver<OneCallModel>() {
                    override fun onSuccess(t: OneCallModel) {
                        oneCall_data.value = t
                        weather_error.value = false
                        weather_load.value = false
                    }

                    override fun onError(e: Throwable) {
                        weather_error.value = true
                        weather_load.value = false
                    }
                })
        )
    }
}