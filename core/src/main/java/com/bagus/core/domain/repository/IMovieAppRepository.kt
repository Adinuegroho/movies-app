package com.bagus.core.domain.repository

import com.bagus.core.data.Resource
import com.bagus.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieAppRepository {

    fun getAllMovies(sort: String): Flow<Resource<List<Movie>>>

    fun getSearchMovies(search: String): Flow<List<Movie>>

    fun getFavoriteMovies(sort: String): Flow<List<Movie>>

    fun setFavoriteMovies(movie: Movie, state: Boolean)

}