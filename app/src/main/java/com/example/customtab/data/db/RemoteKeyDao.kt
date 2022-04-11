package com.example.customtab.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.customtab.data.RemoteKey
import com.example.customtab.data.RemoteKeyContract

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKey>)

    @Query("SELECT * FROM ${RemoteKeyContract.TABLE_NAME} WHERE id = :id")
    suspend fun remoteKeyById(id: String): RemoteKey

    @Query("DELETE FROM ${RemoteKeyContract.TABLE_NAME}")
    suspend fun clearRemoteKeys()
}