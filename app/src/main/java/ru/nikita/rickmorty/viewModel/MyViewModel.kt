package ru.nikita.rickmorty.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response
import ru.nikita.rickmorty.model.Character
import ru.nikita.rickmorty.model.DetailCharacter
import ru.nikita.rickmorty.repository.Repository

class MyViewModel: ViewModel() {
    var repo = Repository()
    var myCharacterList: MutableLiveData<Response<Character>> = MutableLiveData()
    var myDetailList: MutableLiveData<Response<DetailCharacter>> = MutableLiveData()

    fun getMyCharacters(){
        viewModelScope.launch {
            myCharacterList.value = repo.getCharacter()
        }
    }
    fun getDetailCharacters(){
        viewModelScope.launch {
            myDetailList.value = repo.getDetailCharacter()
        }
    }
}
