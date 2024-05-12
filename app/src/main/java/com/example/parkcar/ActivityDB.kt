package com.example.parkcar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import java.util.Date
class ActivityDB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)

        val db= DBHelper(this)
        val data= db.viewAll()
        val date= Date()
        db.insertValues(4.342, 4.1232, "Via Bozzente", date)
        val listView= findViewById<ListView>(R.id.list)
        listView.adapter= CustomAdapter(this, data)
    }


}