package com.example.customtab.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.customtab.data.Food
import com.example.customtab.data.FoodContract

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(food: List<Food>)

    @Query("SELECT * FROM ${FoodContract.TABLE_NAME}")
    fun pagingSource(): PagingSource<Int, Food>

    @Query("DELETE FROM ${FoodContract.TABLE_NAME}")
    suspend fun clearFoods()
}