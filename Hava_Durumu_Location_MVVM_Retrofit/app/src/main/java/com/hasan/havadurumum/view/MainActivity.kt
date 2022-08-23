package com.hasan.havadurumumrc.view

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.hasan.havadurumumrc.R
import com.hasan.havadurumumrc.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var viewmodel: MainViewModel
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private lateinit var GET: SharedPreferences
    private lateinit var SET: SharedPreferences.Editor

    private var actual_latitude: Double = 0.0
    private var actual_longtude: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GET = getSharedPreferences(packageName, MODE_PRIVATE)
        SET = GET.edit()
        viewmodel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val incoming_started_value = intro()

        val cName = GET.getString("cityName", incoming_started_value)?.toLowerCase(Locale.ROOT)
        println("gelen cName degeri:" + cName)
        viewmodel.refreshData(cName!!)
        SET.putString("cityName", cName)
        SET.commit()
        viewmodel.refreshData(cName)
        getLiveData()


        swipe_refresh_layout.setOnRefreshListener {
            dataLinearLayout.visibility = View.GONE
            resultError.visibility = View.GONE
            pb_loading.visibility = View.GONE
            linearlayoutalani.visibility = View.GONE

            val cityName = GET.getString("cityName", cName)?.toLowerCase(Locale.ROOT)
            enterCityName.setText(cityName)
            viewmodel.refreshData(cityName!!)
            swipe_refresh_layout.isRefreshing = false
        }


        imageSearch.setOnClickListener {
            //search will be by city in entered in the blank
            location_active_status.text = getString(R.string.location_not_active_text)
            val cityName = enterCityName.text.toString()
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refreshData(cityName)
            getLiveData()
            Log.i(TAG, "onCreate: " + cityName)
        }


        location_use_image.setOnClickListener {
            //search will be by LOCATİON
            location_active_status.setText(getString(R.string.location_active_text))
            get_location()
            var cityName = getCityName(actual_latitude, actual_longtude)
            println("values returned from the getCityName fun :" + cityName)

            if (cityName == "Not Found") {
                cityName = getCityName(actual_latitude, actual_longtude)
            } else {
                enterCityName.setText(cityName)
                SET.putString("cityName", cityName)
                SET.apply()
                viewmodel.refreshData(cityName!!)
                getLiveData()
                Log.i(TAG, "onCreate: " + cityName)

            }

        }
    }

    fun intro(): String? {
        //search will be by LOCATİON
        location_active_status.setText(getString(R.string.location_active_text))
        get_location()
        var cityName = getCityName(actual_latitude, actual_longtude)
        println("values returned from the getCityName fun :" + cityName)

        if (cityName == "Not Found") {
            cityName = getCityName(actual_latitude, actual_longtude)
        } else {
            enterCityName.setText(cityName)
            SET.putString("cityName", cityName)
            SET.apply()
            viewmodel.refreshData(cityName!!)
            getLiveData()
            Log.i(TAG, "onCreate: " + cityName)

        }
        return cityName

    }

    // take location from user
    private fun get_location() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener =
            object : LocationListener { //We used it like this because it is an interface
                override fun onLocationChanged(p0: Location) {
                    actual_latitude = p0.latitude
                    actual_longtude = p0.longitude
                    println("incoming latitude value :" + actual_latitude)    // optional for logcat screen
                    println("incoming longitude value :" + actual_longtude)      // optional for logcat screen
                }
            }
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) { //ACTIONS TO BE TAKEN IF NOT ALLOWED
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        } else {
            //ACTIONS TO BE TAKEN IF ALLOWED
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                10,
                3f,
                locationListener
            )
        }
    }

    //translate city name from coming location
    private fun getCityName(incomin_latitude: Double, incoming_longitude: Double): String? {
        var cityName: String? = "Not Found"
        val geocoder = Geocoder(this@MainActivity, Locale.getDefault())
        try {
            val adress = geocoder.getFromLocation(incomin_latitude, incoming_longitude, 2)
            if (adress.size > 0) {
                println("full adress information :" + adress)    // optional for logcat screen
                cityName = adress.get(0).adminArea              //adminArea =City name
                println("comeed city name : " + cityName)      // optional for logcat screen
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cityName

    }

    //When we get permission, it sends us the result of the permission.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.size > 0) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    //PERMITTED
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        300000,//5 MINUTE    1000=1 SECOND
                        10f,
                        locationListener
                    )
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getLiveData() {

        viewmodel.weather_data.observe(this) { data ->
            data?.let {
                dataLinearLayout.visibility = View.VISIBLE
                linearlayoutalani.visibility = View.VISIBLE


                Glide.with(this)
                    .load(
                        "https://openweathermap.org/img/wn/" + data.weather
                            .get(0).icon + "@2x.png"
                    ).into(resultWeatherPictures_area)

                city_information_area.text = data.name
                val temp_text_result =
                    data.main.temp.toString() + getString(R.string.selsiyus_symbol)
                temp_celsius_area.text = temp_text_result
                val pressure_text_result =
                    data.main.pressure.toString() + getString(R.string.hPa_presssure_text)
                pressure_data_area.text = pressure_text_result
                val humudity_text_result =
                    data.main.humidity.toString() + getString(R.string.percent_symbol)
                humudity_data_area.text = humudity_text_result
                val wind_text_result =
                    data.wind.speed.toString() + getString(R.string.wind_speed_unit)
                wind_data_area.text = wind_text_result


                weather_status_area.text = data.weather.get(0).description
                val incoming_update_date: Long = data.dt.toLong()
                val incoming_update_hour_text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(incoming_update_date * 1000)
                    )
                val hour_text_result = incoming_update_hour_text + " '"
                update_hour_area.text = hour_text_result

                val incoming_sunrise: Long = data.sys.sunrise.toLong()
                val incoming_sunrise_text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(incoming_sunrise * 1000)
                    )
                sunrise_data_area.text = incoming_sunrise_text

                val incoming_sunset: Long = data.sys.sunset.toLong()
                val incoming_sunset_text =
                    SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(incoming_sunset * 1000)
                    )
                sunset_data_area.text = incoming_sunset_text

            }
        }

        viewmodel.weather_error.observe(this) { error ->
            error?.let {
                if (error) {
                    resultError.visibility = View.GONE
                    TimeUnit.SECONDS.sleep(2L)
                    pb_loading.visibility = View.GONE
                    location_text.setText(getString(R.string.error_text))
                    linearlayoutalani.visibility = View.VISIBLE
                    dataLinearLayout.visibility = View.GONE

                } else {
                    resultError.visibility = View.GONE
                    location_text.setText(getString(R.string.location_text))
                }
            }
        }

        viewmodel.weather_loading.observe(this) { loading ->
            loading?.let {
                if (loading) {
                    pb_loading.visibility = View.VISIBLE
                    linearlayoutalani.visibility = View.GONE
                    resultError.visibility = View.GONE
                    dataLinearLayout.visibility = View.GONE
                } else {
                    pb_loading.visibility = View.GONE
                }
            }
        }

    }

}