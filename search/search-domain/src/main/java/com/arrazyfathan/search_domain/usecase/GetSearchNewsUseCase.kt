package com.arrazyfathan.search_domain.usecase

import com.arrazyfathan.common_utils.Resources
import com.arrazyfathan.search_domain.model.Article
import com.arrazyfathan.search_domain.repository.SearchNewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */
class GetSearchNewsUseCase @Inject constructor(
    private val searchNewsRepository: SearchNewsRepository
) {

    operator fun invoke(queryMap: MutableMap<String, String>): Flow<Resources<List<Article>>> = flow {
        emit(Resources.Loading())
        try {
            val response = searchNewsRepository.searchNews(queryMap)
            emit(Resources.Success(response))
        } catch (e: IOException) {
            emit(Resources.Error("No Internet connection"))
        } catch (e: Exception) {
            emit(Resources.Error(e.message.toString()))
        }
    }
}