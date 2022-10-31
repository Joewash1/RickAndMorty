package com.example.rickandmorty

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.Api.LocationRepo
import com.example.rickandmorty.Api.Network
import com.example.rickandmorty.domain.models.mappers.LocationMapper
import com.example.rickandmorty.view.LocationsUIModel

class LocationsPagingSource(
    private val repository: LocationRepo
    ) : PagingSource<Int, LocationsUIModel>() {
    override fun getRefreshKey(state: PagingState<Int, LocationsUIModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationsUIModel> {
        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1
        val pageRequest = Network.rickAndMortyClient.getLocationsPage(pageNumber)
        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            data = pageRequest.body.results.map { response ->
                LocationsUIModel.Item(LocationMapper.buildFrom(response))
            },
            prevKey = previousKey,
            nextKey = getPageIndexFromNext(pageRequest.body.info.next)
        )
    }

    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}