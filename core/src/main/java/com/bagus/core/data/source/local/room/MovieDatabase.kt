package com.bagus.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bagus.core.data.source.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 4, exportSchema = false)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}