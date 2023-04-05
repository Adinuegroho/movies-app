package com.bagus.moviesapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bagus.core.domain.usecase.MovieAppUseCase
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

class SearchViewModel(private val moviesAppUseCase: MovieAppUseCase): ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        queryChannel.trySend(search).isSuccess
    }

    val movieResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            moviesAppUseCase.getSearchMovies(it)
        }.asLiveData()

}