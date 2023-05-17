package com.arrazyfathan.detail_presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.usecase.GetBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val bookmarkUseCase: GetBookmarkUseCase
) : ViewModel() {

    fun bookmarkArticle(article: Article) {
        viewModelScope.launch {
            bookmarkUseCase.bookmarkArticle(article)
        }
    }

    fun removeBookmark(article: Article) {
        viewModelScope.launch {
            bookmarkUseCase.removeBookmark(article)
        }
    }

    fun checkArticleIsBookmarked(title: String) = bookmarkUseCase.checkIfArticleIsBookmarked(title).asLiveData()
}