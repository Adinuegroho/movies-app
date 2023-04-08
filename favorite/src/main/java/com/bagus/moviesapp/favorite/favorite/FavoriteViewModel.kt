package com.bagus.moviesapp.favorite.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bagus.core.domain.model.Movie
import com.bagus.core.domain.usecase.MovieAppUseCase

class FavoriteViewModel(private val movieAppUseCase: MovieAppUseCase): ViewModel() {

    fun getFavoriteMovies(sort: String): LiveData<List<Movie>> =
        movieAppUseCase.getFavoriteMovies(sort).asLiveData()

    fun setFavorite(Movie: Movie, newState: Boolean) {
        movieAppUseCase.setFavoriteMovies(Movie, newState)
    }
}