package com.bagus.moviesapp.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.bagus.core.data.Resource
import com.bagus.core.domain.model.Movie
import com.bagus.core.domain.usecase.MovieAppUseCase

class MoviesViewModel(private val movieAppUseCase: MovieAppUseCase): ViewModel() {
    fun getMovies(sort: String): LiveData<Resource<List<Movie>>> =
        movieAppUseCase.getAllMovies(sort).asLiveData()
}