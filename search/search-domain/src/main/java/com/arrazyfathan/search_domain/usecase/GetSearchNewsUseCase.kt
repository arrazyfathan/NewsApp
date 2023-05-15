package com.arrazyfathan.search_domain.usecase

import com.arrazyfathan.common_utils.Resources
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.search_domain.repository.SearchNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
class GetSearchNewsUseCase @Inject constructor(
    private val searchNewsRepository: SearchNewsRepository
) {

    operator fun invoke(): Flow<Resources<List<Article>>> = flow {
        emit(Resources.Loading())
        try {
            val response = searchNewsRepository.searchNews()
            emit(Resources.Success(response))
        } catch (e: Exception) {
            emit(Resources.Error("Error"))
        }
    }
}