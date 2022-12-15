package com.skoorc.atvolunteeraid.viewmodel

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.skoorc.atvolunteeraid.R
import com.skoorc.atvolunteeraid.model.Location
import com.skoorc.atvolunteeraid.view.ListFragment

//Recycler view references here
//https://developer.android.com/codelabs/android-room-with-a-view-kotlin#12
class LocationListAdapter internal constructor(context: Context, val listener: ListFragment):
    RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {
    val TAG = "LocationListAdapter"

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var locations = emptyList<Location>()
    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {
        val TAG = "LocationViewHolder"
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val locationItemView: TextView = itemView.findViewById(R.id.latLongTextView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        val reportedByTextView: TextView = itemView.findViewById(R.id.reportedByTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        //  Will attempt to start map activity and zoom to Issue location
        override fun onClick(v: View?) {
            val recyclerPosition: Int = adapterPosition
            val id = idTextView.text.toString().toInt()
            Log.d(TAG, "LocationItemView text:${locationItemView.text}")
            if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemClick(recyclerPosition, id, locationItemView.text.toString())
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val recyclerPosition: Int = adapterPosition
            val idValue = idTextView.text.toString().toInt()
            val latLong = locationItemView.text.toString()
            return if (adapterPosition != RecyclerView.NO_POSITION) {
                listener.onItemLongClick(recyclerPosition, idValue, latLong)
                true
            } else {
                false
            }
        }
    }
    interface OnItemClickListener {
        fun  onItemClick(recyclerPosition: Int, id: Int, location: String)
    }
    interface OnItemLongClickListener {
        fun  onItemLongClick(recyclerPosition: Int, idValue: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return LocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val current = locations[position]
        val latLong = "${current.latitude}\n${current.longitude}"
        holder.idTextView.text = current.id.toString()
        holder.dateTextView.text = current.date
        holder.locationItemView.text = latLong
        holder.typeTextView.text = current.type
        holder.reportedByTextView.text = current.reportedBy
        holder.statusTextView.text = current.status
    }

    internal fun setLocations(locations: List<Location>){
        this.locations = locations
        notifyDataSetChanged()
    }

    override fun getItemCount() = locations.size

//    TODO - Implement Companion DiffCallBack for efficiency
//    companion object DiffCallback : DiffUtil.ItemCallback<foo>() {
//        override fun areItemsTheSame(oldItem: foo, newItem: foo): Boolean {
//            return oldItem === newItem
//        }
//
//        override fun areContentsTheSame(oldItem: foo, newItem: foo): Boolean {
//            return oldItem.id == newItem.id
//        }
}