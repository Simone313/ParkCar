package com.example.parkcar

import java.util.Date

class Location(private val latitude: Double, private val longitude: Double, private val address: String){

    fun getLatitude(): Double{
        return latitude
    }

    fun getLongitude(): Double{
        return longitude
    }

    fun getAddress(): String{
        return address
    }

    override fun toString(): String{
        return "$address Lat: $latitude  Lon: $longitude"
    }
}