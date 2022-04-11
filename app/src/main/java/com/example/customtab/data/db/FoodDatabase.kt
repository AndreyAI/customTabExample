package com.example.customtab.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.customtab.data.Food
import com.example.customtab.data.RemoteKey

@Database(
    entities = [
        Food::class,
        RemoteKey::class
    ], version = FoodDatabase.DB_VERSION
)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "food-database"
    }
}