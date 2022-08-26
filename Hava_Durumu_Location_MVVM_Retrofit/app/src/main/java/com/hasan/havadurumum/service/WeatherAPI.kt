package com.hasan.havadurumumrc.service



import com.hasan.havadurumum.service.id_Information
import com.hasan.havadurumumrc.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

<<<<<<< HEAD
    @GET("data/2.5/weather?&units=metric&APPID=${id_Information.open_weather_id_text}")
    fun getData(@Query("q") cityName: String):Single<WeatherModel>
}





//data/2.5/weather?&units=metric&APPID=${id_Information.open_weather_id_text}
=======
    //http://api.openweathermap.org/data/2.5/weather?q="city_name"&appid="your_api_key"
    // for example;
    //http://api.openweathermap.org/data/2.5/weather?q=paris&appid=12345678910ABCDEFGH
    //http://api.openweathermap.org/data/2.5/weather?lat="your_latitude"&lon="your_longitude"&appid="your_api_key"      //optional

    

    @GET("data/2.5/weather?&units=metric&APPID="your_api_key"")
    fun getData(
        @Query("q") cityName: String): Single<WeatherModel>

}
>>>>>>> 64534f0c4781e78c7c6fca06e3d34cf9579a8c21
