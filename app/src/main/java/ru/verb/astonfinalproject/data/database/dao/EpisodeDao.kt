package ru.verb.astonfinalproject.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.verb.astonfinalproject.data.entity.EpisodeEntity

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM EpisodeEntity ORDER BY id")
    fun getAll(): PagingSource<Int, EpisodeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(episodeEntities: List<EpisodeEntity>)

    @Query("DELETE FROM EpisodeEntity")
    suspend fun clear()

    @Transaction
    suspend fun refresh(episodeEntities: List<EpisodeEntity>){
        clear()
        save(episodeEntities)
    }
}