package com.arrazyfathan.search_presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.common_utils.Resources
import com.arrazyfathan.search_domain.usecase.GetSearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 16/05/23.
 */

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchNewsUseCase: GetSearchNewsUseCase
) : ViewModel() {

    private val _searchArticles = MutableStateFlow(SearchState())
    val searchArticles: StateFlow<SearchState> get() = _searchArticles

    fun getSearchNewsArticles(queryMap: MutableMap<String, String>) {
        getSearchNewsUseCase(queryMap).onEach { resource ->
            when (resource) {
                is Resources.Loading -> {
                    _searchArticles.value = SearchState(isLoading = true)
                }

                is Resources.Error -> {
                    _searchArticles.value = SearchState(isLoading = false, error = resource.message)
                }

                is Resources.Success -> {
                    _searchArticles.value = SearchState(isLoading = false, data = resource.data)
                }
            }
        }.launchIn(viewModelScope)
    }
}