package ru.verb.astonfinalproject.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.verb.astonfinalproject.data.entity.CharacterEntity

@Dao
interface CharacterDao {
    @Query("SELECT * FROM CharacterEntity ORDER BY id")
    fun getAll(): PagingSource<Int, CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(characterEntities: List<CharacterEntity>)

    @Query("DELETE FROM CharacterEntity")
    suspend fun clear()

    @Transaction
    suspend fun refresh(characterEntities: List<CharacterEntity>) {
        clear()
        save(characterEntities)
    }
}