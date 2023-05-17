package com.arrazyfathan.home_presentation.topheadlines

import com.arrazyfathan.home_domain.model.Article

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
data class TopHeadlinesState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: List<Article>? = null
)
