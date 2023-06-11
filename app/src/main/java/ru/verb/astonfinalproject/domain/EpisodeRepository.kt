package ru.verb.astonfinalproject.domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.verb.astonfinalproject.core.BaseRepository
import ru.verb.astonfinalproject.domain.models.Episode


interface EpisodeRepository: BaseRepository<Episode> {
    override fun getPagedItems(): Flow<PagingData<Episode>>
}