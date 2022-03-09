package ru.nikita.rickmorty.repository

import retrofit2.Response
import ru.nikita.rickmorty.adapter.idCharacter
import ru.nikita.rickmorty.api.RetrofitInstance
import ru.nikita.rickmorty.model.Character
import ru.nikita.rickmorty.model.DetailCharacter

class Repository {

    suspend fun getCharacter(page: Int): Response<Character>{
        return RetrofitInstance.api.getCharacters(page)
    }
    suspend fun getDetailCharacter(): Response<DetailCharacter>{
        return RetrofitInstance.api.getDetailCharacter(idCharacter)
    }
}
