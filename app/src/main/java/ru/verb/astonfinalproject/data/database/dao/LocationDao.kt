package ru.verb.astonfinalproject.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import ru.verb.astonfinalproject.data.entity.LocationEntity

@Dao
interface LocationDao {
    @Query("SELECT * FROM LocationEntity ORDER BY id")
    fun getAll(): PagingSource<Int, LocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(locationEntities: List<LocationEntity>)

    @Query("DELETE FROM LocationEntity")
    suspend fun clear()

    @Transaction
    suspend fun refresh(locationEntities: List<LocationEntity>){
        clear()
        save(locationEntities)
    }
}