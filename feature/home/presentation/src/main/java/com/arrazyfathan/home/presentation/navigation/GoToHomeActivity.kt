package com.arrazyfathan.home.presentation.navigation

import android.app.Activity
import android.os.Bundle
import com.arrazyfathan.common.utils.navigator.Navigator
import com.arrazyfathan.home.presentation.HomeActivity

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
object GoToHomeActivity : Navigator {
    override fun navigate(activity: Activity, extras: Bundle?) {
        HomeActivity.launchActivity(activity)
    }
}