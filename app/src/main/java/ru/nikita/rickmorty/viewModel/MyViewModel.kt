package ru.nikita.rickmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.nikita.rickmorty.model.Character
import ru.nikita.rickmorty.model.DetailCharacter
import ru.nikita.rickmorty.repository.Repository

private var pageNumber: Int = 1

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
            myCharacterList.value = repo.getCharacter(page)
        }
    }

    fun getDetailCharacters() {
        viewModelScope.launch {
            myDetailList.value = repo.getDetailCharacter()
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
