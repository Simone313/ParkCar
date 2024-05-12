package com.example.parkcar

import java.util.Date

class Location(latitude: Double, longitude: Double, address: String, date: String){
    companion object{
        private val latitude: Double
            get() {
                return latitude
            }
        private val longitude: Double
            get() {
                return longitude
            }
        private val address: String
            get() {
                return address
            }
        private val date: Date
            get() {
                return date
            }
    }

    fun getLatitude(): Double{
        return latitude
    }
    fun getLongitude(): Double{
        return longitude
    }
    fun getAddress(): String{
        return address
    }
    fun getDate(): Date{
        return date
    }

    /*override fun toString():String{
        return "Lat: $latitude, Lon: $longitude, Add:$address"
    }*/
}