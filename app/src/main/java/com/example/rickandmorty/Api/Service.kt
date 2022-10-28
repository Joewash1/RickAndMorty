package com.example.rickandmorty.Api

import com.example.rickandmorty.model.Result
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("character")
    suspend fun getCharacters(): Call<Any>
    @GET("character/{id}")
    suspend fun getcharactersById(@Path("id") id:Int): Response<Result>
}