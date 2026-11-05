package com.arrazyfathan.search.presentation

import com.arrazyfathan.search.domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 16/05/23.
 */
data class SearchState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<Article>? = null
)