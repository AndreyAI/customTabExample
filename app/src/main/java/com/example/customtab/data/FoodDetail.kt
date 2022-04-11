package com.example.customtab.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodDetail(
    @Json(name = FoodDetailContract.TITLE)
    val title: String,
    @Json(name = FoodDetailContract.YEAR)
    val year: String,
    @Json(name = FoodDetailContract.GENRE)
    val genre: String,
    @Json(name = FoodDetailContract.DIRECTOR)
    val directors: String,
    @Json(name = FoodDetailContract.ACTORS)
    val actors: String,
    @Json(name = FoodDetailContract.POSTER)
    val poster: String,
    @Json(name = FoodDetailContract.RATING)
    val rating: String
)

object FoodDetailContract {
    const val TITLE = "Title"
    const val YEAR = "Year"
    const val GENRE = "Genre"
    const val DIRECTOR = "Director"
    const val ACTORS = "Actors"
    const val POSTER = "Poster"
    const val RATING = "imdbRating"
}