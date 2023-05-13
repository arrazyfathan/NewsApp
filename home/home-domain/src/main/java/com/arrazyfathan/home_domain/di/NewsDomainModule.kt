package com.arrazyfathan.home_domain.di

import com.arrazyfathan.home_domain.repository.BookmarkRepository
import com.arrazyfathan.home_domain.repository.TopHeadlinesRepository
import com.arrazyfathan.home_domain.usecase.GetBookmarkUseCase
import com.arrazyfathan.home_domain.usecase.GetNewsArticleUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

@InstallIn(SingletonComponent::class)
@Module
object NewsDomainModule {

    @Provides
    fun provideNewsArticleUseCase(topHeadlinesRepository: TopHeadlinesRepository): GetNewsArticleUseCase {
        return GetNewsArticleUseCase(topHeadlinesRepository)
    }

    @Provides
    fun provideBookmarkUseCase(bookmarkRepository: BookmarkRepository): GetBookmarkUseCase {
        return GetBookmarkUseCase(bookmarkRepository)
    }
}
