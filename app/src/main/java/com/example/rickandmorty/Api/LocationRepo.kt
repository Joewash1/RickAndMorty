package com.example.rickandmorty.Api

import com.example.rickandmorty.model.characters.GetCharacterResponse
import com.example.rickandmorty.model.locations.LocationResponse

class LocationRepo {
    suspend fun getLocationPage(pageIndex: Int): LocationResponse?{
        val request = Network.rickAndMortyClient.getLocationsPage(pageIndex)

        if (request.failed || !request.isSuccessful){
            return null
        }
        return request.body
    }
}