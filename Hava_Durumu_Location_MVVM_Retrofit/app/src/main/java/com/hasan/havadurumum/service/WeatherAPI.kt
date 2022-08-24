package com.hasan.havadurumumrc.service

import com.hasan.havadurumumrc.model.WeatherModel
import com.hasan.havadurumumrc.view.MainActivity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q="city_name"&appid="your_api_key"
    // for example;
    //http://api.openweathermap.org/data/2.5/weather?q=paris&appid=12345678910ABCDEFGH
    //http://api.openweathermap.org/data/2.5/weather?lat="your_latitude"&lon="your_longitude"&appid="your_api_key"      //optional

    

    @GET("data/2.5/weather?&units=metric&APPID="your_api_key"")
    fun getData(
        @Query("q") cityName: String): Single<WeatherModel>

}
