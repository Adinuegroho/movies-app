package com.bagus.core.data.source.remote

import com.bagus.core.data.source.remote.network.ApiResponse
import com.bagus.core.data.source.remote.network.ApiService
import com.bagus.core.data.source.remote.response.MovieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    private val apiKey = "407e38b098711b86c061e31855c310e0" //REPLACE YOUR API KEY HERE!!

    suspend fun getMovies(): Flow<ApiResponse<List<MovieResponse>>> {
        return flow {
            try {
                val response = apiService.getMovies(apiKey)
                val movieList = response.results
                if (movieList.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }. flowOn(Dispatchers.IO)
    }
}