package com.example.parkcar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.get
import java.util.Date
@Suppress("DEPRECATION")
class ActivityDB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db)

        val db= DBHelper(this)
        //val date= "${Date().day}/${Date().month}/${Date().year}"
        //db.insertValues(4.342, 4.1232, "Via Bozzente", "${Date().day}/${Date().month}/${Date().year}")
        val data= db.viewAll()

        val listView= findViewById<ListView>(R.id.list)
        //listView.adapter= CustomAdapter(this, data)
        listView.adapter= ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data)

        listView.onItemClickListener = AdapterView.OnItemClickListener {  _, _, position, _ ->
            // Ottenere l'indice dell'elemento cliccato
            val index = position
            println("indice"+index)
            val str= data.get(index).split(" ")
            val lat= str.get(3)
            val lon= str.get(6)

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, lat+" "+lon)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)

        }
    }


}