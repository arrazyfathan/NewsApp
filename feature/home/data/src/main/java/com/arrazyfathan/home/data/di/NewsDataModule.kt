package com.arrazyfathan.home.data.di

import com.arrazyfathan.home.data.repository.BookmarkRepositoryImpl
import com.arrazyfathan.home.data.source.remote.network.NewsApiService
import com.arrazyfathan.home.data.repository.TopHeadlinesRepositoryImpl
import com.arrazyfathan.home.data.source.local.db.BookmarkDao
import com.arrazyfathan.home.domain.repository.BookmarkRepository
import com.arrazyfathan.home.domain.repository.TopHeadlinesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

@InstallIn(SingletonComponent::class)
@Module
object NewsDataModule {

    @Provides
    fun provideNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }

    @Provides
    fun provideTopHeadlineRepository(newsApiService: NewsApiService): TopHeadlinesRepository {
        return TopHeadlinesRepositoryImpl(newsApiService)
    }

    @Provides
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao): BookmarkRepository {
        return BookmarkRepositoryImpl(bookmarkDao)
    }
}
