package com.hasan.havadurumumrc.model


import com.google.gson.annotations.SerializedName
import com.hasan.havadurumumrc.service.WeatherAPI
import io.reactivex.Single

data class WeatherModel(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind,



)