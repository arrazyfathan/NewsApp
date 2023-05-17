package com.arrazyfathan.search_domain.repository

import com.arrazyfathan.search_domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
interface SearchNewsRepository {

    suspend fun searchNews(queryMap: MutableMap<String, String>): List<Article>
}