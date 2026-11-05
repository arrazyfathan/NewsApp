package com.arrazyfathan.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrazyfathan.common.utils.Resources
import com.arrazyfathan.search.domain.usecase.GetSearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * Created by Ar Razy Fathan Rabbani on 16/05/23.
 */

@HiltViewModel
class SearchViewModel
    @Inject
    constructor(
        private val getSearchNewsUseCase: GetSearchNewsUseCase,
    ) : ViewModel() {
        val searchArticles: StateFlow<SearchState>
            field = MutableStateFlow(SearchState())

        fun getSearchNewsArticles(queryMap: MutableMap<String, String>) {
            getSearchNewsUseCase(queryMap)
                .onEach { resource ->
                    when (resource) {
                        is Resources.Loading -> {
                            searchArticles.value = SearchState(isLoading = true)
                        }

                        is Resources.Error -> {
                            searchArticles.value = SearchState(isLoading = false, error = resource.message)
                        }

                        is Resources.Success -> {
                            searchArticles.value = SearchState(isLoading = false, data = resource.data)
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }
