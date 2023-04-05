package com.bagus.moviesapp.di

import com.bagus.core.domain.usecase.MovieAppInteractor
import com.bagus.core.domain.usecase.MovieAppUseCase
import com.bagus.moviesapp.home.SearchViewModel
import com.bagus.moviesapp.movies.MoviesViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieAppUseCase> {MovieAppInteractor(get())}
}

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}