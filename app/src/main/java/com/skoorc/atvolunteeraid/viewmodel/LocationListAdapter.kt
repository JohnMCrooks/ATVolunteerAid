package com.skoorc.atvolunteeraid.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.model.Location

//Recycler view references here
//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
class LocationListAdapter internal constructor(context: Context):
    RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<Location>()
    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val locationItemView: TextView = itemView.findViewById(R.id.latLongTextView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return LocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val current = locations[position]
        val latLong = "${current.latitude}, ${current.longitude}"
        holder.dateTextView.text = current.date
        holder.locationItemView.text = latLong
        holder.typeTextView.text = current.type
    }

    internal fun setLocations(locations: List<Location>){
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size
}