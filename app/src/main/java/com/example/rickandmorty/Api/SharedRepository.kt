package com.example.rickandmorty.Api

import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Locations
import com.example.rickandmorty.domain.models.mappers.CharacterMapper
import com.example.rickandmorty.domain.models.mappers.LocationMapper


class SharedRepository {
    suspend fun getCharacterById(id: Int): Character? {
        val request = Network.rickAndMortyClient.getCharacterById(id)

        if(request.failed){
            return null
        }

        if (!request.isSuccessful){
            return null
        }
        return CharacterMapper.buildFrom(response = request.body)
    }
    suspend fun getLocationById(id: Int): Locations? {
        val request = Network.rickAndMortyClient.getLocationsById(id)

        if(request.failed){
            return null
        }

        if (!request.isSuccessful){
            return null
        }
        return LocationMapper.buildFrom(response = request.body)
    }
}