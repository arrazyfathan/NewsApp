package com.arrazyfathan.detail.presentation.navigation

import android.app.Activity
import android.os.Bundle
import com.arrazyfathan.common.utils.navigator.Navigator
import com.arrazyfathan.detail.presentation.DetailArticleActivity

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */
object GoToDetailActivity : Navigator {

    override fun navigate(activity: Activity, extras: Bundle?) {
        DetailArticleActivity.launchActivity(activity, extras)
    }
}