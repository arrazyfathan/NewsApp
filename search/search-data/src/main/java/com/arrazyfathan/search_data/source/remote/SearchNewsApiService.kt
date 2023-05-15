package com.arrazyfathan.search_data.source.remote

import com.arrazyfathan.home_data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
interface SearchNewsApiService {

    @GET("everything")
    suspend fun searchNews(
        @Query("q") keyword: String
    ) : NewsResponse
}