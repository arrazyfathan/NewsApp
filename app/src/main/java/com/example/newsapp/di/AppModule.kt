package com.example.newsapp.di

import com.arrazyfathan.common_utils.navigator.Navigator
import com.example.newsapp.navigation.DefaultNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}
