package com.arrazyfathan.newsapp

import android.app.Application
import com.pluto.Pluto
import com.pluto.plugins.network.PlutoNetworkPlugin
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class NewsApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        Pluto.Installer(this)
            .addPlugin(PlutoNetworkPlugin())
            .install()
    }
}
