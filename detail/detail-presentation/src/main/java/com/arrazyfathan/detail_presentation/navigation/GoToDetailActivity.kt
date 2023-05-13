package com.arrazyfathan.detail_presentation.navigation

import android.app.Activity
import android.os.Bundle
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.detail_presentation.DetailArticleActivity

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */
object GoToDetailActivity : Navigator {

    override fun navigate(activity: Activity, extras: Bundle?) {
        DetailArticleActivity.launchActivity(activity, extras)
    }
}