package ru.verb.astonfinalproject.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.CardLocationBinding
import ru.verb.astonfinalproject.domain.models.Location

typealias OnLocationInteractionListener = (Location) -> Unit

class LocationAdapter(
    private val onLocationInteractionListener: OnLocationInteractionListener
) : PagingDataAdapter<Location, LocationViewHolder>(LocationDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = CardLocationBinding.inflate(LayoutInflater.from(parent.context))
        return LocationViewHolder(binding, onLocationInteractionListener, parent.context)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.tag = item
        item?.let {
            holder.bind(it)
        }
    }
}

class LocationViewHolder(
    private val binding: CardLocationBinding,
    private val onLocationInteractionListener: OnLocationInteractionListener,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    fun bind(location: Location) {
        with(binding) {
            locationName.text = context.getString(R.string.location_name, location.name)
            locationType.text = context.getString(R.string.location_type, location.type)
            locationDimension.text = context.getString(R.string.location_dimension, location.dimension)
        }
    }

    override fun onClick(v: View) {
        val location = v.tag as Location
        onLocationInteractionListener.invoke(location)
    }
}

class LocationDiffCallBack : DiffUtil.ItemCallback<Location>() {

    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

}