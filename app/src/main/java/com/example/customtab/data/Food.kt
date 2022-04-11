package com.example.customtab.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WrapFood(
    @Json(name = "Search")
    val search: List<Food>,
    @Json(name = "totalResults")
    val results: String,
    @Json(name = "Response")
    val response: String
)

@Entity(
    tableName = FoodContract.TABLE_NAME
)
@JsonClass(generateAdapter = true)
data class Food(
    @PrimaryKey
    @Json(name = FoodContract.Columns.ID)
    val id: String,
    @Json(name = FoodContract.Columns.TITLE)
    val title: String,
    @Json(name = FoodContract.Columns.YEAR)
    val year: String,
    @Json(name = FoodContract.Columns.TYPE)
    val type: String,
    @Json(name = FoodContract.Columns.POSTER)
    val poster: String
)

object FoodContract {
    const val TABLE_NAME = "food"

    object Columns {
        const val ID = "imdbID"
        const val TITLE = "Title"
        const val YEAR = "Year"
        const val TYPE = "Type"
        const val POSTER = "Poster"
    }
}