package com.arrazyfathan.search.data.source.remote

import com.arrazyfathan.search.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
interface SearchNewsApiService {
    @GET("everything")
    suspend fun searchNews(
        @QueryMap query: MutableMap<String, String>,
    ): NewsResponse
}
