package ru.verb.astonfinalproject.presentation.adapter.location

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.CardLocationBinding
import ru.verb.astonfinalproject.domain.models.Location

typealias OnLocationClickListener = (Location) -> Unit

class LocationAdapter(
    private val onLocationClickListener: OnLocationClickListener
) : PagingDataAdapter<Location, LocationViewHolder>(LocationDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val binding = CardLocationBinding.inflate(LayoutInflater.from(parent.context))
        return LocationViewHolder(binding, onLocationClickListener, parent.context)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }
}

class LocationViewHolder(
    private val binding: CardLocationBinding,
    private val onLocationClickListener: OnLocationClickListener,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(location: Location) {
        with(binding) {
            locationName.text = context.getString(R.string.location_name, location.name)
            locationType.text = context.getString(R.string.location_type, location.type)
            locationDimension.text = context.getString(R.string.location_dimension, location.dimension)
            locationAvatar.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.rick_and_morty_location))
            root.setOnClickListener {
                onLocationClickListener.invoke(location)
            }
        }
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