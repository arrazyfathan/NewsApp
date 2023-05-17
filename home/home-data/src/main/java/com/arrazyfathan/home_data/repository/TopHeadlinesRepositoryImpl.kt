package com.arrazyfathan.home_data.repository

import android.util.Log
import com.arrazyfathan.home_data.mapper.toDomainArticle
import com.arrazyfathan.home_data.source.remote.network.NewsApiService
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.repository.TopHeadlinesRepository
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
class TopHeadlinesRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
) : TopHeadlinesRepository {

    override suspend fun getNewsArticles(page: Int, pageSize: Int): List<Article> {
        return newsApiService.getNewsArticles(page = page, pageSize = pageSize).articles.map {
            it.toDomainArticle()
        }
    }
}
