package com.bagus.core.domain.usecase

import com.bagus.core.data.Resource
import com.bagus.core.domain.model.Movie
import com.bagus.core.domain.repository.IMovieAppRepository
import kotlinx.coroutines.flow.Flow

class MovieAppInteractor(private val iMovieAppRepository: IMovieAppRepository): MovieAppUseCase {
    override fun getAllMovies(sort: String): Flow<Resource<List<Movie>>> {
        return iMovieAppRepository.getAllMovies(sort)
    }

    override fun getSearchMovies(search: String): Flow<List<Movie>> {
        return iMovieAppRepository.getSearchMovies(search)
    }

    override fun getFavoriteMovies(sort: String): Flow<List<Movie>> {
        return iMovieAppRepository.getFavoriteMovies(sort)
    }

    override fun setFavoriteMovies(movie: Movie, state: Boolean) {
        return iMovieAppRepository.setFavoriteMovies(movie, state)
    }
}