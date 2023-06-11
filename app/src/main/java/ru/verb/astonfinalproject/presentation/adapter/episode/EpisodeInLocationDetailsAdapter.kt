package ru.verb.astonfinalproject.presentation.adapter.episode

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.CardEpisodeInLocationDetailsBinding
import ru.verb.astonfinalproject.domain.models.Episode

class EpisodeInLocationDetailsAdapter(
    private val onEpisodeClickListener: OnEpisodeClickListener
) : ListAdapter<Episode, EpisodeInLocationDetailsViewHolder>(EpisodeDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeInLocationDetailsViewHolder {
        val context = parent.context
        val binding = CardEpisodeInLocationDetailsBinding.inflate(LayoutInflater.from(context))
        return EpisodeInLocationDetailsViewHolder(binding, onEpisodeClickListener, context)
    }

    override fun onBindViewHolder(holder: EpisodeInLocationDetailsViewHolder, position: Int) {
        val episode = getItem(position)
        holder.bind(episode)
    }
}

class EpisodeInLocationDetailsViewHolder(
    private val binding: CardEpisodeInLocationDetailsBinding,
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