package com.example.rickandmorty.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.rickandmorty.Api.CharactersRepo
import com.example.rickandmorty.CharacterPagingSource
import com.example.rickandmorty.Constants
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.view.CharacterUIModel
import kotlinx.coroutines.flow.map


class CharactersViewModel : ViewModel() {

    private val repo = CharactersRepo()
    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )

    ) {
        CharacterPagingSource(repo)
    }.flow.cachedIn(viewModelScope)
}