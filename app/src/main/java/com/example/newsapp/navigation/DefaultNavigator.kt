package com.example.newsapp.navigation

import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.common_utils.navigator.Screen
import com.arrazyfathan.detail_presentation.navigation.GoToDetailActivity
import com.arrazyfathan.home_presentation.navigation.GoToHomeActivity
import com.arrazyfathan.search_presentation.GoToSearchActivity

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
