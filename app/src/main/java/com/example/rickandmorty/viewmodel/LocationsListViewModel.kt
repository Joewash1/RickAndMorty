package com.example.rickandmorty.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.rickandmorty.Api.LocationRepo
import com.example.rickandmorty.Constants
import com.example.rickandmorty.LocationsPagingSource
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.model.Location
import kotlinx.coroutines.launch

class LocationsListViewModel: ViewModel() {

    private val repo = LocationRepo()

    private val _locationByIdLiveData = MutableLiveData<Location?>()
    val locationByIdLiveData: LiveData<Location?> = _locationByIdLiveData

    val flow = Pager(
        PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            prefetchDistance = Constants.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )

    ) {
        LocationsPagingSource(repo)
    }.flow.cachedIn(viewModelScope)
}