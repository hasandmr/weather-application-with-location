package com.hasan.havadurumumrc.service



import com.hasan.havadurumum.service.id_Information
import com.hasan.havadurumumrc.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

<<<<<<< HEAD
=======

>>>>>>> e0465ece44dfab1d992edf349fd5a7fda78a0808
    @GET("data/2.5/weather?&units=metric&APPID=${id_Information.open_weather_id_text}")
    fun getData(@Query("q") cityName: String):Single<WeatherModel>
}

<<<<<<< HEAD




//data/2.5/weather?&units=metric&APPID=${id_Information.open_weather_id_text}
=======
>>>>>>> e0465ece44dfab1d992edf349fd5a7fda78a0808
