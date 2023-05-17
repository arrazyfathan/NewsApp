package com.arrazyfathan.home_presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.arrazyfathan.common_utils.Resources
import com.arrazyfathan.home_data.source.remote.TopHeadlinesDataSource
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.repository.TopHeadlinesRepository
import com.arrazyfathan.home_domain.usecase.GetBookmarkUseCase
import com.arrazyfathan.home_domain.usecase.GetNewsArticleUseCase
import com.arrazyfathan.home_presentation.topheadlines.TopHeadlinesState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

@HiltViewModel
class TopHeadlinesViewModel @Inject constructor(
    private val getNewsArticleUseCase: GetNewsArticleUseCase,
    private val topHeadlinesRepository: TopHeadlinesRepository,
    private val bookmarkUseCase: GetBookmarkUseCase
) : ViewModel() {

    var lastFirstVisiblePosition = 0

    private val _isGridLayoutManager = MutableStateFlow(true)
    val isGridLayoutManager: StateFlow<Boolean> = _isGridLayoutManager

    private val _topHeadlines = MutableStateFlow(TopHeadlinesState())
    val topHeadlines: StateFlow<TopHeadlinesState> = _topHeadlines

    val topHeadlinesPager = Pager(
        config = PagingConfig(pageSize = 10)
    ) {
        TopHeadlinesDataSource(topHeadlinesRepository)
    }.flow.cachedIn(viewModelScope)

    val bookmarkedArticles = bookmarkUseCase.getBookmarkedArticles().asLiveData()

    fun checkArticleIsBookmarked(title: String) =
        bookmarkUseCase.checkIfArticleIsBookmarked(title).asLiveData()

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

    fun setLayoutManager(isGrid: Boolean) {
        viewModelScope.launch {
            _isGridLayoutManager.value = isGrid
        }
    }

    private fun getNewsArticles() {
        getNewsArticleUseCase().onEach {
            when (it) {
                is Resources.Error -> {
                    _topHeadlines.value = TopHeadlinesState(error = it.message)
                    Log.d("response", it.message)
                }

                is Resources.Loading -> {
                    _topHeadlines.value = TopHeadlinesState(isLoading = true)
                }

                is Resources.Success -> {
                    _topHeadlines.value = TopHeadlinesState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}
