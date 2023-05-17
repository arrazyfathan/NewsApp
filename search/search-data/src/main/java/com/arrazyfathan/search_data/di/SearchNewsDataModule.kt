package com.arrazyfathan.search_data.di

import com.arrazyfathan.search_data.repository.SearchNewsRepositoryImpl
import com.arrazyfathan.search_data.source.remote.SearchNewsApiService
import com.arrazyfathan.search_domain.repository.SearchNewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Created by Ar Razy Fathan Rabbani on 15/05/23.
 */

@Module
@InstallIn(SingletonComponent::class)
object SearchNewsDataModule {

    @Provides
    fun provideSearchNewsApiService(retrofit: Retrofit): SearchNewsApiService {
        return retrofit.create(SearchNewsApiService::class.java)
    }

    @Provides
    fun provideSearchNewRepository(searchNewsApiService: SearchNewsApiService): SearchNewsRepository {
        return SearchNewsRepositoryImpl(searchNewsApiService)
    }
}