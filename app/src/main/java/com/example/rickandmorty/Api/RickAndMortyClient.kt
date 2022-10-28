package com.example.rickandmorty.Api

import com.example.rickandmorty.model.Result
import com.example.rickandmorty.model.SimpleResponse
import retrofit2.Response

class RickAndMortyClient(private val service: Service) {
    suspend fun getCharacterById(id: Int): SimpleResponse<Result> {
        return safeApiCall { service.getcharactersById(id) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T>{
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e:java.lang.Exception){
            SimpleResponse.failure(e)
        }
    }
}