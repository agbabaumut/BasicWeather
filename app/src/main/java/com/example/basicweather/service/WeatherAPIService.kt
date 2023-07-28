package com.example.basicweather.service

import com.example.basicweather.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class WeatherAPIService {

    //http://api.openweathermap.org/data/2.5/weather?q=istanbul&APPID=04a42b96398abc8e4183798ed22f9485

    // Define the base URL for the OpenWeatherMap API
    private val BASE_URL = "http://api.openweathermap.org/"

    // Create a Retrofit instance using the builder pattern
    private val api = Retrofit.Builder()
        // Set the base URL for the API
        .baseUrl(BASE_URL)
        // Add Gson converter factory to parse JSON responses into data classes
        .addConverterFactory(GsonConverterFactory.create())
        // Add RxJava2 call adapter factory to handle asynchronous operations with RxJava
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        // Build the Retrofit instance
        .build()
        // Create an implementation of the WeatherAPI interface using the Retrofit instance
        .create(WeatherAPI::class.java)

    // Define a function to get weather data from the service
    fun getDataFromService(cityName: String): Single<WeatherModel> {
        // Call the corresponding method in the WeatherAPI interface to get the data for the specified city
        return api.getData(cityName)
    }


}