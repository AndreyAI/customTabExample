package com.example.customtab.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RemoteKeyContract.TABLE_NAME)
data class RemoteKey(
    @PrimaryKey
    val id: String,
    val nextKey: Int?,
    val prevKey: Int?
)

object RemoteKeyContract {
    const val TABLE_NAME = "remote_key"
}

