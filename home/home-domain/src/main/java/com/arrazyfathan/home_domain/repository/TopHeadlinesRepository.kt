package com.arrazyfathan.home_domain.repository

import com.arrazyfathan.home_domain.model.Article
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

interface TopHeadlinesRepository {
    suspend fun getNewsArticles(page: Int, pageSize: Int): List<Article>
}
