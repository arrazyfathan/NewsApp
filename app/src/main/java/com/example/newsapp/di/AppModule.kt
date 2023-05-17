package com.example.newsapp.di

import android.content.Context
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.home_data.source.local.db.BookmarkDao
import com.example.newsapp.db.AppDatabase
import com.example.newsapp.navigation.DefaultNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNavigation(): Navigator.Provider {
        return DefaultNavigator()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideBookmarkDao(appDatabase: AppDatabase): BookmarkDao {
        return appDatabase.getBookmarkDao()
    }
}
