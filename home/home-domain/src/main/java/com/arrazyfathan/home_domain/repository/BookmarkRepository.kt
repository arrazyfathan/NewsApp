package com.arrazyfathan.home_domain.repository

import com.arrazyfathan.home_domain.model.Article
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */
interface BookmarkRepository {
    fun getBookmarkedArticles(): Flow<List<Article>>
    suspend fun bookmarkArticle(article: Article)
    suspend fun removeBookmark(article: Article)
    fun checkArticleIsBookmarked(title: String): Flow<Boolean>
}