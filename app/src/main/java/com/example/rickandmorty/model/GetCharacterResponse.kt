package com.example.rickandmorty.model

data class GetCharacterResponse(
    val info: Info,
    val results: List<Result>
)