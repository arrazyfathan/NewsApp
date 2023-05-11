package com.arrazyfathan.home_data.source.remote.network

import com.arrazyfathan.common_utils.Constants
import com.arrazyfathan.home_data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
interface NewsApiService {

    @GET("top-headlines")
    suspend fun getNewsArticles(
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 10,
        @Query("country") countryCode: String = Constants.COUNTRY,
        @Query("apiKey") apiKey: String = Constants.API_KEY,
    ): NewsResponse
}
