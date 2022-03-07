package ru.nikita.rickmorty.viewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.nikita.rickmorty.model.Character
import ru.nikita.rickmorty.model.DetailCharacter
import ru.nikita.rickmorty.repository.Repository
import kotlin.contracts.contract

private var pageNumber: Int = 1
private var filter: String = ""

class MyViewModel : ViewModel() {
    var repo = Repository()
    var myCharacterList: MutableLiveData<Response<Character>> = MutableLiveData()
    var myCurrentList: MutableLiveData<Response<Character>> = MutableLiveData()
    var myDetailList: MutableLiveData<Response<DetailCharacter>> = MutableLiveData()
    private var isQueryAvailable = MutableLiveData<Boolean>()

    fun getMyCharacters(page: Int) {
        pageNumber = page
        isQueryAvailable.value = false
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                kotlin.runCatching { repo.getCharacter(page) }
            }
            result.onSuccess { myCharacterList.value = it }
        }
    }

    fun getDetailCharacters() {
        viewModelScope.launch {
            myDetailList.value = repo.getDetailCharacter()
        }
    }
    fun getFilterCharacters(query: String?) {
        viewModelScope.launch {
            myCharacterList.value = repo.getFilteredCharacter(filter)
        }
    }

    fun searchNextPage() {
        viewModelScope.launch {
            if (pageNumber != 52){
                getMyCharacters(pageNumber+1)
            }
        }
    }
}


