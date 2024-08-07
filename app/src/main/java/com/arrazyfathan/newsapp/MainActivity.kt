package com.arrazyfathan.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.arrazyfathan.common_utils.getDayOfWeek
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.common_utils.navigator.Screen
import com.arrazyfathan.newsapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigation: Navigator.Provider

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        lifecycleScope.launch {
            delay(500L)
            navigation.getScreen(Screen.HomeActivity).navigate(this@MainActivity)
            finish()
        }
    }

    private fun setupView() = with(binding) {
        newsToday.text = getDayOfWeek()
    }
}
