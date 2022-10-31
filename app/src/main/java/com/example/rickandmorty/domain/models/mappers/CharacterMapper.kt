package com.example.rickandmorty.domain.models.mappers

import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.model.Location
import com.example.rickandmorty.model.Origin
import com.example.rickandmorty.model.Result

object CharacterMapper {

    fun buildFrom(response: Result): Character{
        return Character(
            episode = response.episode,
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )

    }
}