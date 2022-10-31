package com.example.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.Api.SharedRepository
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.Locations
import kotlinx.coroutines.launch

class SharedViewModel: ViewModel() {

    private val repository = SharedRepository()

    private val _charactersLiveData = MutableLiveData<Character?>()
    val charactersLiveData: LiveData<Character?> = _charactersLiveData

    private val _locationsLiveData = MutableLiveData<Locations?>()
    val locationsLiveData: LiveData<Locations?> = _locationsLiveData

    fun refreshCharacter(id: Int){
            viewModelScope.launch {
                val response = repository.getCharacterById(id)

                _charactersLiveData.postValue(response)
            }
    }

    fun refreshLocations(id: Int){
        viewModelScope.launch {
            val response = repository.getLocationById(id)

            _locationsLiveData.postValue(response)
        }
    }


}