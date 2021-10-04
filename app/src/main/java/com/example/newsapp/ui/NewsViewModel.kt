package com.example.newsapp.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.NewsApplication
import com.example.newsapp.models.Article
import com.example.newsapp.models.NewsResponse
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.util.Resources
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    app: Application,
    val newsRepository: NewsRepository
) : AndroidViewModel(app) {

    val breakingNews: MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resources<NewsResponse>> = MutableLiveData()
    var pageSearchNews = 1
    var searchNewsResponse: NewsResponse? = null
    var newSearchQuery:String? = null
    var oldSearchQuery:String? = null

    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        safeBreakingNewsCall(countryCode)
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
       safeSearchNewsCall(searchQuery)
    }

    private fun handlingNewsResponse(response: Response<NewsResponse>): Resources<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = resultResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resources.Success(breakingNewsResponse ?: resultResponse)
            }
        }
        return Resources.Error(response.message())
    }

    private fun handlingSearchResponse(response: Response<NewsResponse>): Resources<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                pageSearchNews++
                if(searchNewsResponse == null || newSearchQuery != oldSearchQuery) {
                    pageSearchNews = 1
                    oldSearchQuery = newSearchQuery
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)

                }
                return Resources.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resources.Error(response.message())
    }

    //Save Article in room database
    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteSavedNews(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    private suspend fun safeBreakingNewsCall(countryCode: String) {
        breakingNews.postValue(Resources.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handlingNewsResponse(response))
            } else {
                breakingNews.postValue(Resources.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> breakingNews.postValue(Resources.Error("Network Failure"))
                else -> breakingNews.postValue(Resources.Error("Conversion Error"))
            }
        }
    }

    private suspend fun safeSearchNewsCall(searchQuery: String) {
        newSearchQuery = searchQuery
        searchNews.postValue(Resources.Loading())
        try {
            if(hasInternetConnection()) {
                val response = newsRepository.searchNews(searchQuery, pageSearchNews)
                searchNews.postValue(handlingSearchResponse(response))
            } else {
                searchNews.postValue(Resources.Error("No internet connection"))
            }
        } catch(t: Throwable) {
            when(t) {
                is IOException -> searchNews.postValue(Resources.Error("Network Failure"))
                else -> searchNews.postValue(Resources.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when(type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}