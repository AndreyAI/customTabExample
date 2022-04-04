package com.example.customtab.data

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

@JsonClass(generateAdapter = true)
data class Food(
    @Json(name = "imdbID")
    val id: String,
    @Json(name = "Title")
    val title: String,
    @Json(name = "Year")
    val year: String,
    @Json(name = "Type")
    val type: String,
    @Json(name = "Poster")
    val poster: String
)