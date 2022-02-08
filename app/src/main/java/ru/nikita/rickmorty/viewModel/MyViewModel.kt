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

class MyViewModel: ViewModel() {
    var repo = Repository()
    var myCharacterList: MutableLiveData<Response<Character>> = MutableLiveData()
    var myDetailList: MutableLiveData<Response<DetailCharacter>> = MutableLiveData()
    private var isQueryAvailable = MutableLiveData<Boolean>()
    var pageNumber: Int = 1

    fun getMyCharacters(page: Int){
        viewModelScope.launch {
            isQueryAvailable.value = false
            pageNumber = page
            myCharacterList.value = repo.getCharacter(pageNumber)
        }
    }
    fun getDetailCharacters(){
        viewModelScope.launch {
            myDetailList.value = repo.getDetailCharacter()
        }
    }
    fun searchNextPage(){
        if (!isQueryAvailable().value!!){
            viewModelScope.launch {
                pageNumber+=1
                myCharacterList.value = repo.getCharacter(pageNumber)
            }
        }
    }
    private fun isQueryAvailable(): LiveData<Boolean> {
        if(pageNumber == 50 ) isQueryAvailable.value = true
        return isQueryAvailable
    }
}
