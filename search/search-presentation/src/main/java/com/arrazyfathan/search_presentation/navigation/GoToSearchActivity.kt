package com.arrazyfathan.search_presentation.navigation

import android.app.Activity
import android.os.Bundle
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.search_presentation.SearchActivity

/**
 * Created by Ar Razy Fathan Rabbani on 16/05/23.
 */

object GoToSearchActivity : Navigator {
    override fun navigate(activity: Activity, extras: Bundle?) {
        SearchActivity.launchActivity(activity)
    }
}