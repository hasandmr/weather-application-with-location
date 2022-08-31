package com.hasan.havadurumumrc.service



import com.hasan.havadurumum.service.id_Information
import com.hasan.havadurumumrc.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?&units=metric&APPID=${id_Information.open_weather_id_text}")
    fun getData(@Query("q") cityName: String):Single<WeatherModel>
}





//data/2.5/weather?&units=metric&APPID=${id_Information.open_weather_id_text}