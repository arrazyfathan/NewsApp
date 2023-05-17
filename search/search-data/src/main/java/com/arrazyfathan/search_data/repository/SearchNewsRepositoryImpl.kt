package com.arrazyfathan.search_data.repository

import com.arrazyfathan.search_data.mapper.toDomainArticle
import com.arrazyfathan.search_data.source.remote.SearchNewsApiService
import com.arrazyfathan.search_domain.model.Article
import com.arrazyfathan.search_domain.repository.SearchNewsRepository
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
class SearchNewsRepositoryImpl @Inject constructor(
    private val searNewsApiService: SearchNewsApiService
) : SearchNewsRepository {

    override suspend fun searchNews(queryMap: MutableMap<String, String>): List<Article> {
        return searNewsApiService.searchNews(queryMap).articles.map {
            it.toDomainArticle()
        }
    }
}