package com.arrazyfathan.search.domain.repository

import com.arrazyfathan.search.domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
interface SearchNewsRepository {

    suspend fun searchNews(queryMap: MutableMap<String, String>): List<Article>
}