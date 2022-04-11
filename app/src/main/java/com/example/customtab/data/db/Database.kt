package com.example.customtab.data.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: FoodDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            FoodDatabase.DB_NAME
        )
            .build()
    }
}