package ru.nikita.rickmorty.api

import retrofit2.Response
import retrofit2.http.GET
import ru.nikita.rickmorty.model.Character

interface ApiService {
    @GET("character")
    suspend fun getCharacters(): Response<Character>
}