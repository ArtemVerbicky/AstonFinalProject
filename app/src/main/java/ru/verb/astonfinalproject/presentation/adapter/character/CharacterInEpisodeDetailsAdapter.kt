package ru.verb.astonfinalproject.presentation.adapter.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.verb.astonfinalproject.databinding.CardCharacterBinding
import ru.verb.astonfinalproject.domain.models.Character

class CharacterInEpisodeDetailsAdapter(
    private val onCharacterClickListener: OnCharacterClickListener
) : ListAdapter<Character, CharacterViewHolder>(CharacterDiffCallBack()) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CardCharacterBinding.inflate(LayoutInflater.from(parent.context))
        return CharacterViewHolder(binding, onCharacterClickListener, parent.context)
    }
}