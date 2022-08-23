package com.hasan.havadurumumrc.service

import com.hasan.havadurumumrc.model.WeatherModel
import com.hasan.havadurumumrc.view.MainActivity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=ankara&appid=139c27b32236861c5cf5de2e3cdde6d5

    //http://api.openweathermap.org/data/2.5/weather?lat=38&lon=27&appid=139c27b32236861c5cf5de2e3cdde6d5      //optional

    // val url="data/2.5/weather?lat="+guncel_latitude+"&lon="+guncel_longtude+"&APPID=139c27b32236861c5cf5de2e3cdde6d5"  //optional

    @GET("data/2.5/weather?&units=metric&APPID=139c27b32236861c5cf5de2e3cdde6d5")
    fun getData(
        @Query("q") cityName: String): Single<WeatherModel>

}