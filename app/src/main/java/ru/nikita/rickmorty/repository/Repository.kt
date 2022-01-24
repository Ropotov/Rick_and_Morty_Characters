package ru.nikita.rickmorty.repository

import retrofit2.Response
import ru.nikita.rickmorty.api.RetrofitInstance
import ru.nikita.rickmorty.model.Character

class Repository {

    suspend fun getCharacter(): Response<Character>{
        return RetrofitInstance.api.getCharacters()
    }
    suspend fun getDetailCharacter(): Response<Character>{
        return RetrofitInstance.api.getDetailCharacter(1)
    }
}
