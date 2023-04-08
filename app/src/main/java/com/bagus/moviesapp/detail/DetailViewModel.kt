package com.bagus.moviesapp.detail

import androidx.lifecycle.ViewModel
import com.bagus.core.domain.model.Movie
import com.bagus.core.domain.usecase.MovieAppUseCase

class DetailViewModel(private val movieAppUseCase: MovieAppUseCase): ViewModel() {
    fun setFavoriteMovie(movie: Movie, newStatus: Boolean) {
        movieAppUseCase.setFavoriteMovies(movie, newStatus)
    }
}