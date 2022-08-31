package com.hasan.havadurumum.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.hasan.havadurumumrc.view.MainActivity
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*


class MainActivityTest {

    @Before

    //here we check if the city name is taken
    @Test
    fun getCityName() {
        val lates = 41.01
        val longi = 28.97

        val mainActivity = MainActivity()

        val cityname = "İstanbul"
        val incoming_value = mainActivity.getCityName(lates, longi) //Here, we are wait , get the City Name

        Assert.assertTrue("ERROR", cityname == incoming_value) //control
    }

    //here we check if the location is taken
    @Test
    fun get_locationTest() {

        val lates = 41.01
        val longi = 28.97

        val mainActivity = MainActivity()

        mainActivity.get_location()  //Here, we are wait , get the location

        val incoming_lates = mainActivity.actual_latitude
        val incoming_longi = mainActivity.actual_longtude

        Assert.assertTrue("ERROR LATITUDE", lates == incoming_lates)   //control
        Assert.assertTrue("ERROR LONGITUDE", longi == incoming_longi)  //control
    }

    //here we check if the permission is taken
    @Test
    fun requestpermissionstest(){
        val perms =Array<String>(1) {"android.permission.FINE_LOCATION"}
        val requestcode=1
        val grantresult = intArrayOf(0,1)
        onRequestPermissionsResultTest(requestcode,perms,grantresult)

    }
     fun onRequestPermissionsResultTest(
        requestCode: Int ,
        permissions: Array<out String> ,
        grantResults: IntArray
    ) {
        val mainActivity=MainActivity()
         if (requestCode == 1) {
            if (grantResults.size > 0) {
                if (ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    //PERMITTED
                }
                assertTrue("ERROR permission", permissions.equals(
                    "android.permission.FINE_LOCATION"))   //control
            }
            assertNull("ERROR grantResult is null",grantResults )   //control
        }
        assertTrue("ERROR requestCode", requestCode == 1)   //control
     }
}