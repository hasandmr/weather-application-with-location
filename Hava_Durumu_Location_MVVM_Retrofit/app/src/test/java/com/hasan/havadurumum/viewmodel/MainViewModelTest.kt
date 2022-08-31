package com.hasan.havadurumum.viewmodel

import com.hasan.havadurumumrc.viewmodel.MainViewModel
import org.junit.Assert.*
import org.junit.Test

class MainViewModelTest {

    //here we check if the Data is taken
    @Test
    fun getDataFromAPITest() {

        val city = "ankara"
        val viewmodel = MainViewModel()
        viewmodel.getDataFromAPI(city)
        val incoming_value = viewmodel.weather_data.value  //Here, we are wait , get the DATA
        assertNull("ERROR data not Found", incoming_value)  //control

    }




}