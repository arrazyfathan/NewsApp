package com.arrazyfathan.search_data.repository

import com.arrazyfathan.home_data.mapper.toDomainArticle
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.search_data.source.remote.SearchNewsApiService
import com.arrazyfathan.search_domain.repository.SearchNewsRepository
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
class SearchNewsRepositoryImpl @Inject constructor(
    private val searNewsApiService: SearchNewsApiService
) : SearchNewsRepository {

    override suspend fun searchNews(): List<Article> {
        return searNewsApiService.searchNews("").articles.map {
            it.toDomainArticle()
        }
    }
}