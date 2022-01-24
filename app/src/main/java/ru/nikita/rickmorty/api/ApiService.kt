package ru.nikita.rickmorty.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.nikita.rickmorty.model.Character

interface ApiService {
    @GET("character")
    suspend fun getCharacters(): Response<Character>

    @GET("character/")
    suspend fun getDetailCharacter(@Query("id") id: Int): Response<Character>
}