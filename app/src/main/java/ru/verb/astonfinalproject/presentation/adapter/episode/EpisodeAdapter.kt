package ru.verb.astonfinalproject.presentation.adapter.episode

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.CardEpisodeBinding
import ru.verb.astonfinalproject.domain.models.Episode

typealias OnEpisodeClickListener = (Episode) -> Unit

class EpisodeAdapter(
    private val onEpisodeClickListener: OnEpisodeClickListener
) : PagingDataAdapter<Episode, EpisodeViewHolder>(EpisodeDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = CardEpisodeBinding.inflate(LayoutInflater.from(parent.context))
        return EpisodeViewHolder(binding, onEpisodeClickListener, parent.context)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }

}

class EpisodeViewHolder(
    private val binding: CardEpisodeBinding,
    private val onEpisodeClickListener: OnEpisodeClickListener,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(episode: Episode) {
        with(binding) {
            episodeAvatar.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    R.drawable.rick_and_morty_episode
                )
            )
            episodeName.text = context.getString(R.string.episode_name, episode.name)
            episodeAirDate.text = context.getString(R.string.episode_air_date_txt, episode.airDate)
            episodeSe.text = context.getString(R.string.episode, episode.episode)
            root.setOnClickListener {
                onEpisodeClickListener.invoke(episode)
            }
        }
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