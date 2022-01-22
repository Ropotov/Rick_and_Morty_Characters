package ru.nikita.rickmorty.repository

import retrofit2.Response
import ru.nikita.rickmorty.api.RetrofitInstance
import ru.nikita.rickmorty.model.Character

class Repository {

    suspend fun getCharacter(): Response<Character>{
        return RetrofitInstance.api.getCharacters()
    }
}
