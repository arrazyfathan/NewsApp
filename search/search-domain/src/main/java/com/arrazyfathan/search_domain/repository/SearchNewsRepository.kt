package com.arrazyfathan.search_domain.repository

import com.arrazyfathan.home_domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
interface SearchNewsRepository {

    suspend fun searchNews(): List<Article>
}