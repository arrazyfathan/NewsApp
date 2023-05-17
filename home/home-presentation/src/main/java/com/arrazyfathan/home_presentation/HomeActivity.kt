package com.arrazyfathan.home_presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.arrazyfathan.home_presentation.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: TopHeadlinesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNavigation()
    }

    private fun setupNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationBar.setupWithNavController(navController)
        binding.bottomNavigationBar.itemIconTintList = null
        binding.bottomNavigationBar.background = null

        binding.bottomNavigationBar.setOnItemSelectedListener { item ->
            val currentDestination = navController.currentDestination
            if (item.itemId == currentDestination?.id) return@setOnItemSelectedListener false
            navController.navigate(item.itemId)
            return@setOnItemSelectedListener true
        }

        viewModel.bookmarkedArticles.observe(this) { articles ->
            binding.bottomNavigationBar.getOrCreateBadge(R.id.bookmarkFragment).apply {
                if (articles.isNotEmpty()) {
                    backgroundColor = getColor(R.color.black)
                    isVisible = true
                    number = articles.size
                } else {
                    isVisible = false
                }
            }
        }
    }

    companion object {
        fun launchActivity(activity: Activity) {
            val intent = Intent(activity, HomeActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
