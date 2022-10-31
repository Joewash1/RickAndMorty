package com.example.rickandmorty.model.characters

import com.example.rickandmorty.model.Result

data class GetCharacterResponse(
    val info: Info,
    val results: List<Result> = emptyList()
)