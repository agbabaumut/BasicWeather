package com.example.basicweather.service

import com.example.basicweather.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "547bde66153b0e91f0fb6ed56c963f9d"

interface WeatherAPI {
    // My API = http://api.openweathermap.org/data/2.5/weather?q=istanbul&APPID=04a42b96398abc8e4183798ed22f9485&units=metric
    // The query q is our desired location name that we want to learn the weather of.

    @GET("data/2.5/weather?&units=metric&APPID=$API_KEY")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>
}