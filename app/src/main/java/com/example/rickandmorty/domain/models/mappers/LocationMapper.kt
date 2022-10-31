package com.example.rickandmorty.domain.models.mappers

import com.example.rickandmorty.domain.models.Locations
import com.example.rickandmorty.model.Location
import com.example.rickandmorty.model.Origin
import com.example.rickandmorty.model.locations.ResultX

object LocationMapper {

    fun buildFrom(response: ResultX): Locations {
        return Locations(
            created = response.created,
            dimension = response.dimension,
            id = response.id,
            name = response.name,
            residents = response.residents,
            type = response.type,
            url = response.url
        )
    }
}