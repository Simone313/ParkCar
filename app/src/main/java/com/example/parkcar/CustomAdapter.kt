package com.example.parkcar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomAdapter(val context: Context, val data: ArrayList<Location>): BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Location {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var newView= convertView
        if(newView==null){
            newView= LayoutInflater.from(context).inflate(
                R.layout.activity_custom_adapter, parent, false
            )
        }
        val title= newView?.findViewById<TextView>(R.id.Title)
        val desc= newView?.findViewById<TextView>(R.id.Description)

        val d= data[position]
        title?.text= d.getAddress()
        desc?.text= "Latitude: ${d.getLatitude()}, Longitude: ${d.getLongitude()}, Date: ${d.getDate().toString()}"

        return newView!!
    }

}