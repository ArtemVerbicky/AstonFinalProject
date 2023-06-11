package ru.verb.astonfinalproject.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.verb.astonfinalproject.R
import ru.verb.astonfinalproject.databinding.CardCharacterBinding
import ru.verb.astonfinalproject.domain.models.Character
import ru.verb.astonfinalproject.utils.loadImage

typealias OnCharacterInteractionListener = (Character) -> Unit

class CharacterAdapter(
    private val onCharacterInteractionListener: OnCharacterInteractionListener
): PagingDataAdapter<Character, CharacterViewHolder>(CharacterDiffCallBack()) {

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.tag = item
        item?.let {
            holder.bind(it)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = CardCharacterBinding.inflate(LayoutInflater.from(parent.context))
        return CharacterViewHolder(binding, onCharacterInteractionListener, parent.context)
    }
}

class CharacterViewHolder(
    private val binding: CardCharacterBinding,
    private val onInteractionListener: OnCharacterInteractionListener,
    private val context: Context
): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    fun bind(character: Character) {
        with(binding) {
            characterName.text = context.getString(R.string.character_name, character.name)
            characterGender.text = context.getString(R.string.character_gender, character.gender)
            characterStatus.text = context.getString(R.string.character_status, character.status.status)
            characterSpecies.text = context.getString(R.string.character_species, character.species)
            characterAvatar.loadImage(character.image)
        }
    }

    override fun onClick(v: View) {
        val character = v.tag as Character
        onInteractionListener.invoke(character)
    }
}

class CharacterDiffCallBack: DiffUtil.ItemCallback<Character>() {

    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }

}