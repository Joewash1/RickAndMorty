package com.example.rickandmorty.Api

import com.example.rickandmorty.model.Result
import retrofit2.Response

class SharedRepository {
    suspend fun getCharacterById(id: Int): Result? {
        val request = Network.rickAndMortyClient.getCharacterById(id)

        if(request.failed){
            return null
        }

        if (!request.isSuccessful){
            return null
        }
        return request.body
    }
}