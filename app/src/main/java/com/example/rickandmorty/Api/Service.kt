package com.example.rickandmorty.Api

import com.example.rickandmorty.model.Location
import com.example.rickandmorty.model.characters.GetCharacterResponse
import com.example.rickandmorty.model.Result
import com.example.rickandmorty.model.locations.LocationResponse
import com.example.rickandmorty.model.locations.ResultX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("character")
    suspend fun getCharactersPage(@Query("page") pageIndex: Int): Response<GetCharacterResponse>
    @GET("character/{id}")
    suspend fun getcharactersById(@Path("id") id:Int): Response<Result>
    @GET("location")
    suspend fun getLocationsPage(@Query("page") pageIndex: Int): Response<LocationResponse>
    @GET("location/{id}")
    suspend fun getlocationsById(@Path("id") id:Int): Response<ResultX>
}