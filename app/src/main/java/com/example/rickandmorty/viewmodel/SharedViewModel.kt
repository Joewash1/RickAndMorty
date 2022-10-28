package com.example.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.Api.SharedRepository
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val repository = SharedRepository()

    private val _charactersLiveData = MutableLiveData<com.example.rickandmorty.model.Result>()
    val charactersLiveData: LiveData<com.example.rickandmorty.model.Result> = _charactersLiveData

    fun refreshCharacter(id: Int){
            viewModelScope.launch {
                val response = repository.getCharacterById(id)

                _charactersLiveData.postValue(response)
            }
    }
}