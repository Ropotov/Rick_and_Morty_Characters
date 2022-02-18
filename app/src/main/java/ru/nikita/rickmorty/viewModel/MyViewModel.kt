package ru.nikita.rickmorty.viewModel

import androidx.lifecycle.LiveData
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
private var pageNumber: Int = 1
private var queryName: String? = ""


class MyViewModel : ViewModel() {
    var repo = Repository()
    var myCharacterList: MutableLiveData<Response<Character>> = MutableLiveData()
    var myCurrentList: MutableLiveData<Response<Character>> = MutableLiveData()
    var myDetailList: MutableLiveData<Response<DetailCharacter>> = MutableLiveData()
    private var isQueryAvailable = MutableLiveData<Boolean>()

    fun getMyCharacters(page: Int, name: String?) {
        pageNumber = page
        queryName = name
        viewModelScope.launch {
            isQueryAvailable.value = false
            myCharacterList.value = repo.getCharacter(page, queryName)
        }
    }

    fun getDetailCharacters() {
        viewModelScope.launch {
            myDetailList.value = repo.getDetailCharacter()
        }
    }

    fun searchNextPage() {
        viewModelScope.launch {
            if (pageNumber != 50){
                getMyCharacters(pageNumber+1, queryName)
            }
        }
    }
}

