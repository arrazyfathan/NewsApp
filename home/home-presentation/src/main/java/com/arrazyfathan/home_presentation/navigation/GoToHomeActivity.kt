package com.arrazyfathan.home_presentation.navigation

import android.app.Activity
import android.os.Bundle
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.home_presentation.HomeActivity

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
object GoToHomeActivity : Navigator {
    override fun navigate(activity: Activity, extras: Bundle?) {
        HomeActivity.launchActivity(activity)
    }
}