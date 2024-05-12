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
                        "$COLUMN_ADD STRING,"+
                        "$COLUMN_DT DATE)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS "+ TABLENAME)
        onCreate(db)
    }

    fun insertValues(lat: Double, lon: Double, add: String, date: Date){
        val db= this.writableDatabase
        val data= ContentValues()
        data.put(COLUMN_LAT, lat)
        data.put(COLUMN_LON, lon)
        data.put(COLUMN_ADD, add)
        data.put(COLUMN_DT, date.toString())
        db.insert(TABLENAME, null, data)
        db.close()
    }

    fun viewAll(): ArrayList<Location>{
        val locations= ArrayList<Location>()
        val db= this.readableDatabase
        val cursor= db.rawQuery("SELECT * FROM $TABLENAME", null)
        if(cursor.moveToFirst()){
            do{
                locations.add(
                    Location((cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LAT))),
                        (cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LAT))),
                        (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADD))),
                        (cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DT))))
                )
            }while(cursor.moveToNext())
        }
        db.close()
        return locations
    }



}