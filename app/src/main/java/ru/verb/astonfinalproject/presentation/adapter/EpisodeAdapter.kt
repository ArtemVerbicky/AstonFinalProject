package ru.verb.astonfinalproject.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.CardEpisodeBinding
import ru.verb.astonfinalproject.domain.models.Episode

typealias OnEpisodeInteractionListener = (Episode) -> Unit

class EpisodeAdapter(
    private val onEpisodeInteractionListener: OnEpisodeInteractionListener
) : PagingDataAdapter<Episode, EpisodeViewHolder>(EpisodeDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = CardEpisodeBinding.inflate(LayoutInflater.from(parent.context))
        return EpisodeViewHolder(binding, onEpisodeInteractionListener, parent.context)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.tag = item
        item?.let {
            holder.bind(it)
        }
    }

}

class EpisodeViewHolder(
    private val binding: CardEpisodeBinding,
    private val onEpisodeInteractionListener: OnEpisodeInteractionListener,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    fun bind(episode: Episode) {
        with(binding) {
            episodeName.text = context.getString(R.string.episode_name, episode.name)
            episodeAirDate.text = context.getString(R.string.episode_air_date_txt, episode.airDate)
            episodeSe.text = context.getString(R.string.episode, episode.episode)
        }
    }

    override fun onClick(v: View) {
        val episode = v.tag as Episode
        onEpisodeInteractionListener.invoke(episode)
    }
}

class EpisodeDiffCallBack : DiffUtil.ItemCallback<Episode>() {
    override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
        return oldItem == newItem
    }
}