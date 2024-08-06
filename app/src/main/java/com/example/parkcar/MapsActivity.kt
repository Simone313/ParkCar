package com.example.parkcar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.parkcar.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        intent = getIntent()
        var lat= intent.getDoubleExtra("Latitude",0.0)
        var lon= intent.getDoubleExtra("Longitude",0.0)
        var add= intent.getStringExtra("Address")
        val pos = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(pos).title("Marker"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos))
        // creo l'oggetto LatLing
        val latlng= LatLng(lat,lon)
        //metodo per far si che la camera faccia uno zoom sulla posizione
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18.0f))

        mMap.setOnMarkerClickListener {marker->
            Toast.makeText(this, add, Toast.LENGTH_SHORT).show()
            true
        }

    }


}