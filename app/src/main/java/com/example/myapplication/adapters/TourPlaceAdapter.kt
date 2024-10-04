package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data_class.TourPlace

class TourPlaceAdapter(private val tourPlaces: List<TourPlace>) :
    RecyclerView.Adapter<TourPlaceAdapter.TourPlaceViewHolder>() {


    class TourPlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tourPlaceCity: TextView = itemView.findViewById(R.id.tourPlaceCity)
        private val tourPlaceTopic: TextView = itemView.findViewById(R.id.tourPlaceTopic)
        private val tourPlaceDescription: TextView = itemView.findViewById(R.id.tourPlaceDescription)
        private val tourPlaceImage: ImageView = itemView.findViewById(R.id.tourPlaceImage)

        fun bind(tourPlace: TourPlace) {
            tourPlaceCity.text = tourPlace.city
            tourPlaceTopic.text = tourPlace.topic
            tourPlaceDescription.text = tourPlace.description

            // Use Glide to load the image from URL
            /*Glide.with(itemView.context)
                .load(tourPlace.imageUrl)
                .into(tourPlaceImage)*/

        }

    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): TourPlaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tour_place, parent, false)
        return TourPlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: TourPlaceAdapter.TourPlaceViewHolder, position: Int) {
        val tourPlace = tourPlaces[position]
        holder.bind(tourPlace)
    }

    override fun getItemCount() = tourPlaces.size

}

