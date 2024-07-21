/*
* App per memorizzare la posizione dell'auto (tramite geolocalizzazione con GPS), ricordare i posti
* nella quale viene parcheggiata (inserendo i dati in un database) e in grado di condividere una
* posizione salvata.
* */

package com.example.parkcar

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
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
import java.util.Date
import android.location.LocationListener
import android.location.LocationRequest
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {
    val PERMISSION_FINE_LOCATION=99;
    var latitude=0.0
    var longitude=0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn= findViewById<Button>(R.id.locationBtn)
        btn.setOnClickListener {
            saveGPS()
        }

        val viewAllBtn= findViewById<Button>(R.id.viewAllBtn)
        viewAllBtn.setOnClickListener {
            viewAll()
        }

        val mapBtn= findViewById<Button>(R.id.mapBtn)

        mapBtn.setOnClickListener {
            viewMap()
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
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this){location ->
                val database= DBHelper(this)

                var latTxt= findViewById<TextView>(R.id.latTxt)
                var lonTxt= findViewById<TextView>(R.id.lonTxt)
                var addTxt= findViewById<TextView>(R.id.addTxt)
                latitude= location.latitude
                longitude= location.longitude
                latTxt.text= latitude.toString()
                lonTxt.text= longitude.toString()

                var geocoder= Geocoder(this)
                var address= (geocoder.getFromLocation(latitude, longitude ,1)?.get(0)?.thoroughfare).toString()
                addTxt.text=address
                val dat= Date().toString()
                val la= latitude.toString()
                val lo= longitude.toString()
                database.insertValue(la, lo, address, dat)

            }

        }else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_FINE_LOCATION)
            }
        }

    }

    fun viewAll(){
        val intentBtn= Intent(this, ActivityDB:: class.java)
        startActivity(intentBtn)
    }

    fun viewMap(){
        val intent= Intent(this, MapsActivity:: class.java)
        intent.putExtra("Latitude", latitude)
        intent.putExtra("Longitude", longitude)
        startActivity(intent)
    }


}




