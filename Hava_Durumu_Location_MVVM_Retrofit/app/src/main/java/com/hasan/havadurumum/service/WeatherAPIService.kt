package com.hasan.havadurumumrc.service

import com.hasan.havadurumumrc.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {
<<<<<<< HEAD

    //http://api.openweathermap.org/data/2.5/weather?q=ankara&appid=139c27b32236861c5cf5de2e3cdde6d5

=======
>>>>>>> e0465ece44dfab1d992edf349fd5a7fda78a0808
    private val BASE_URL = "http://api.openweathermap.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        .create(WeatherAPI::class.java)
  
    fun getDataService(cityName: String): Single<WeatherModel> {
        return api.getData(cityName)

<<<<<<< HEAD
    }
}
=======

    }
   }
  }
>>>>>>> e0465ece44dfab1d992edf349fd5a7fda78a0808
