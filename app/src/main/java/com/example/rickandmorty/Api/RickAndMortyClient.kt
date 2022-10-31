package com.example.rickandmorty.Api

import com.example.rickandmorty.model.Location
import com.example.rickandmorty.model.characters.GetCharacterResponse
import com.example.rickandmorty.model.Result
import com.example.rickandmorty.model.SimpleResponse
import com.example.rickandmorty.model.locations.LocationResponse
import com.example.rickandmorty.model.locations.ResultX
import retrofit2.Response

class RickAndMortyClient(private val service: Service) {
    suspend fun getCharacterById(id: Int): SimpleResponse<Result> {
        return safeApiCall { service.getcharactersById(id) }
    }
    suspend fun getCharactersPage(pageIndex: Int) : SimpleResponse<GetCharacterResponse>{
        return safeApiCall { service.getCharactersPage(pageIndex) }
    }
    suspend fun getLocationsById(id: Int): SimpleResponse<ResultX> {
        return safeApiCall { service.getlocationsById(id) }
    }
    suspend fun getLocationsPage(pageIndex: Int) : SimpleResponse<LocationResponse>{
        return safeApiCall { service.getLocationsPage(pageIndex) }
    }


    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T>{
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e:java.lang.Exception){
            SimpleResponse.failure(e)
        }
    }
}