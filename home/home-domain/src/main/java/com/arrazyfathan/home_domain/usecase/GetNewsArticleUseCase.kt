package com.arrazyfathan.home_domain.usecase

import android.util.Log
import com.arrazyfathan.common_utils.Resources
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.repository.TopHeadlinesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
class GetNewsArticleUseCase @Inject constructor(
    private val topHeadlinesRepository: TopHeadlinesRepository,
) {

    operator fun invoke(): Flow<Resources<List<Article>>> = flow {
        emit(Resources.Loading())
        try {
            emit(Resources.Success(data = topHeadlinesRepository.getNewsArticles(1, 10) ))
        } catch (e: Exception) {
            emit(Resources.Error(e.message.toString()))
        }
    }
}
