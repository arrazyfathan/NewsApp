package com.arrazyfathan.common_utils.navigator

import android.app.Activity
import android.os.Bundle

/**
 * Created by Ar Razy Fathan Rabbani on 10/05/23.
 */
interface Navigator {
    fun navigate(activity: Activity, extras: Bundle? = null)

    interface Provider {
        fun getScreen(screen: Screen): Navigator
    }
}
