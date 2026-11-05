package com.arrazyfathan.newsapp.navigation

import com.arrazyfathan.common.utils.navigator.Navigator
import com.arrazyfathan.common.utils.navigator.Screen
import com.arrazyfathan.detail.presentation.navigation.GoToDetailActivity
import com.arrazyfathan.home.presentation.navigation.GoToHomeActivity
import com.arrazyfathan.search.presentation.navigation.GoToSearchActivity

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */

class DefaultNavigator : Navigator.Provider {

    override fun getScreen(screen: Screen): Navigator {
        return when (screen) {
            Screen.HomeActivity -> GoToHomeActivity
            Screen.SearchActivity -> GoToSearchActivity
            Screen.DetailScreen -> GoToDetailActivity
        }
    }
}
