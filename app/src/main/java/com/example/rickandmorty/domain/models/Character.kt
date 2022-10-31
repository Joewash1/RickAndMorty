package com.example.rickandmorty.domain.models

import com.example.rickandmorty.model.Location
import com.example.rickandmorty.model.Origin

data class Character(
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
)
data class Location(
    val name: String,
    val url: String
)
data class Origin(
    val name: String,
    val url: String
)
