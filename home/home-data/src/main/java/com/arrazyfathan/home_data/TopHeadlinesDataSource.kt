package com.arrazyfathan.home_data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_domain.repository.TopHeadlinesRepository
import java.io.IOException
import java.lang.Exception

/**
 * Created by Ar Razy Fathan Rabbani on 11/05/23.
 */
class TopHeadlinesDataSource(
    private val topHeadlinesRepository: TopHeadlinesRepository
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        return try {
            val page = params.key ?: 1
            val response = topHeadlinesRepository.getNewsArticles(page, DEFAULT_PAGE_SIZE)
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isNotEmpty()) page + 1 else null,
            )
        } catch (e: IOException) {
            LoadResult.Error(NoInternetException())
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }
}

class NoInternetException(message: String = "No internet connection") : IOException(message)