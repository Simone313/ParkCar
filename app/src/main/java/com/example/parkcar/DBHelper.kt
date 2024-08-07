package com.example.parkcar

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.Date
class DBHelper (context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DBVERSION){

    companion object{
        private val DATABASE_NAME="Locations"
        private val DBVERSION =1
        private val TABLENAME="LocationTable"
        private val COLUMN_ID="id"
        private val COLUMN_LAT= "Latitude"
        private val COLUMN_LON="Longitude"
        private val COLUMN_ADD="Address"
        private val COLUMN_DT="Date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLENAME("+
                        "$COLUMN_ID INTEGER PRIMARY KEY,"+
                        "$COLUMN_LAT DOUBLE,"+
                        "$COLUMN_LON DOUBLE,"+
                        "$COLUMN_ADD STRING ,"+
                        "$COLUMN_DT STRING)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS "+ TABLENAME)
        onCreate(db)
    }

    fun insertValue(lat: String, lon: String, add: String, date: String){
        val db= this.writableDatabase
        val data= ContentValues()

        data.put(COLUMN_LAT, lat)
        data.put(COLUMN_LON, lon)
        data.put(COLUMN_ADD, add)
        data.put(COLUMN_DT, date)
        db.insert(TABLENAME, null, data)
        db.close()
    }

    fun viewAll(): ArrayList<String>{
        val locations= ArrayList<String>()
        val db= this.readableDatabase
        val cursor= db.rawQuery("SELECT * FROM $TABLENAME", null)
        if(cursor.moveToFirst()){
            do{
                val ind= (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADD)))
                val lat= (cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LAT)))
//                val dat= (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DT)))
                val lon= (cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LON)))

                var loc= Location(lat, lon, ind)

                locations.add(loc.toString())
            }while(cursor.moveToNext())
        }
        db.close()
        return locations
    }



}