/*
* App per memorizzare la posizione dell'auto (tramite geolocalizzazione con GPS), ricordare i posti
* nella quale viene parcheggiata (inserendo i dati in un database) e in grado di condividere una
* posizione salvata.
* */

package com.example.parkcar

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.location.Location
import android.os.Build
import android.widget.TextView
import android.widget.Toast

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
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener{
                fun onSuccess(location: Location){
                    var latTxt= findViewById<TextView>(R.id.latTxt)
                    var lonText= findViewById<TextView>(R.id.lonTxt)
                    latTxt.setText(location.latitude.toInt())
                    lonText.setText(location.longitude.toInt())
                }
            }
        }else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_FINE_LOCATION)
            }
        }

    }


}