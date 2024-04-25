/*
* App per memorizzare la posizione dell'auto (tramite geolocalizzazione con GPS), ricordare i posti
* nella quale viene parcheggiata (inserendo i dati in un database) e in grado di condividere una
* posizione salvata.
* */

package com.example.parkcar

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import android.location.Geocoder

class MainActivity : AppCompatActivity() {
    val PERMISSION_FINE_LOCATION=99;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn= findViewById<Button>(R.id.locationBtn)
        btn.setOnClickListener {
            saveGPS()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSION_FINE_LOCATION){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                saveGPS()
            }else{
                Toast.makeText(this, "This app requires permission to be granted in order to work properly", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }



    private fun saveGPS(){

        var fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this)

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            var locationManager = getSystemService(LOCATION_SERVICE) as LocationManager


            var provider = LocationManager.GPS_PROVIDER

            val location: Location? = locationManager.getLastKnownLocation(provider.toString())


            if (location != null) {

                var latTxt= findViewById<TextView>(R.id.latTxt)
                var lonTxt= findViewById<TextView>(R.id.lonTxt)
                var addTxt= findViewById<TextView>(R.id.addTxt)
                var latitude= location.latitude
                var longitude= location.longitude
                latTxt.text= latitude.toString()
                lonTxt.text= longitude.toString()

                var geocoder= Geocoder(this)
                var address= geocoder.getFromLocation(latitude, longitude ,1)?.get(0)?.thoroughfare
                addTxt.text=address.toString()

            }
        }else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_FINE_LOCATION)
            }
        }

    }


}