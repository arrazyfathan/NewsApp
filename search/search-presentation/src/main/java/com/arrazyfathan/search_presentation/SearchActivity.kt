package com.arrazyfathan.search_presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arrazyfathan.common_utils.navigator.Navigator

class SearchActivity : AppCompatActivity() {

    companion object {
        fun launchActivity(activity: Activity) {
            activity.startActivity(Intent(activity, SearchActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}

object GoToSearchActivity : Navigator {
    override fun navigate(activity: Activity, extras: Bundle?) {
        SearchActivity.launchActivity(activity)
    }
}
