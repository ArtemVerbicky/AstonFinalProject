package ru.verb.astonfinalproject.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.verb.astonfinalproject.data.database.dao.CharacterDao
import ru.verb.astonfinalproject.data.database.dao.EpisodeDao
import ru.verb.astonfinalproject.data.database.dao.LocationDao
import ru.verb.astonfinalproject.data.entity.CharacterEntity
import ru.verb.astonfinalproject.data.entity.EpisodeEntity
import ru.verb.astonfinalproject.data.entity.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class, EpisodeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun episodeDao(): EpisodeDao

//    companion object {
//        @Volatile
//        private var instance: AppDb? = null
//
//        fun getInstance(context: Context): AppDb {
//            return instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//
//        private fun buildDatabase(context: Context): AppDb {
//            return Room.databaseBuilder(context, AppDb::class.java, "app.db")
//                .fallbackToDestructiveMigration()
//                .build()
//        }
//    }
}