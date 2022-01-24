package ru.nikita.rickmorty.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.nikita.rickmorty.model.Character
import ru.nikita.rickmorty.model.DetailCharacter

interface ApiService {
    @GET("character")
    suspend fun getCharacters(): Response<Character>

    @GET("character/{id}")
    suspend fun getDetailCharacter(@Path("id") id:Int): Response<DetailCharacter>
}