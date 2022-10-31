package com.example.rickandmorty.Api

import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.mappers.CharacterMapper
import com.example.rickandmorty.model.characters.GetCharacterResponse

class CharactersRepo {

    suspend fun getCharacterPage(pageIndex: Int): GetCharacterResponse?{
        val request = Network.rickAndMortyClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful){
            return null
        }
        return request.body
    }
    suspend fun getCharacterById(characterId: Int): Character? {


        val request = Network.rickAndMortyClient.getCharacterById(characterId)

        if (request.failed || !request.isSuccessful) {
            return null
        }


        val character = CharacterMapper.buildFrom(
            response = request.body
        )


        return character
    }
}