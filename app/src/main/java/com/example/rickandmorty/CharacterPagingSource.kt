package com.example.rickandmorty

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.Api.CharactersRepo
import com.example.rickandmorty.Api.Network
import com.example.rickandmorty.domain.models.Character
import com.example.rickandmorty.domain.models.mappers.CharacterMapper
import com.example.rickandmorty.view.CharacterUIModel

class CharacterPagingSource(
    private val repository: CharactersRepo
) : PagingSource<Int, CharacterUIModel>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterUIModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterUIModel> {
        val pageNumber = params.key ?: 1
        val previousKey = if (pageNumber == 1) null else pageNumber - 1
        val pageRequest = Network.rickAndMortyClient.getCharactersPage(pageNumber)
        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(data = pageRequest.body.results.map { response ->
            CharacterUIModel.Item(CharacterMapper.buildFrom(response))
        },
                               prevKey = previousKey,
                               nextKey = getPageIndexFromNext(pageRequest.body.info.next))
    }
    private fun getPageIndexFromNext(next: String?): Int? {
        return next?.split("?page=")?.get(1)?.toInt()
    }
}