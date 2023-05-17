package com.arrazyfathan.home_domain.usecase

import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.repository.BookmarkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */
class GetBookmarkUseCase @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {

    fun getBookmarkedArticles(): Flow<List<Article>> = bookmarkRepository.getBookmarkedArticles()

    suspend fun bookmarkArticle(article: Article) {
        bookmarkRepository.bookmarkArticle(article)
    }

    suspend fun removeBookmark(article: Article) {
        bookmarkRepository.removeBookmark(article)
    }

    fun checkIfArticleIsBookmarked(title: String): Flow<Boolean> {
        return bookmarkRepository.checkArticleIsBookmarked(title)
    }
}