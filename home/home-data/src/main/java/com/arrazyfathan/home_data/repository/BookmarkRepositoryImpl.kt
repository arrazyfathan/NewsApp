package com.arrazyfathan.home_data.repository

import com.arrazyfathan.home_data.mapper.toArticleEntity
import com.arrazyfathan.home_data.mapper.toDomainArticle
import com.arrazyfathan.home_data.source.local.db.BookmarkDao
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */
class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao
) : BookmarkRepository {

    override fun getBookmarkedArticles(): Flow<List<Article>> {
        return bookmarkDao.getBookmarkedArticle().map { articles ->
            articles.map { it.toDomainArticle() }
        }
    }

    override suspend fun bookmarkArticle(article: Article) = withContext(Dispatchers.IO) {
        return@withContext bookmarkDao.upsertArticle(article.copy(isBookmarked = true).toArticleEntity())
    }

    override suspend fun removeBookmark(article: Article) {
        withContext(Dispatchers.IO) {
            bookmarkDao.deleteArticle(article.toArticleEntity())
        }
    }

    override fun checkArticleIsBookmarked(title: String): Flow<Boolean> {
        return bookmarkDao.checkArticleIsBookmarked(title)
    }
}