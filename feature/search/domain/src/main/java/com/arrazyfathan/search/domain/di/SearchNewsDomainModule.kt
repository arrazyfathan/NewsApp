package com.arrazyfathan.search.domain.di

import com.arrazyfathan.search.domain.repository.SearchNewsRepository
import com.arrazyfathan.search.domain.usecase.GetSearchNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */

@Module
@InstallIn(SingletonComponent::class)
class SearchNewsDomainModule {

    @Provides
    fun provideSearchNewsUseCase(searchNewsRepository: SearchNewsRepository): GetSearchNewsUseCase {
        return GetSearchNewsUseCase(searchNewsRepository)
    }
}