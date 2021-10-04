package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)
        bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        bottomNavigationView.itemIconTintList = null



        viewModel.getSavedNews().observe(this, Observer {

                bottomNavigationView.getOrCreateBadge(R.id.bookmarkedFragment).apply {
                    if (it.isNotEmpty()) {
                        backgroundColor = getColor(R.color.black)
                        isVisible = true
                        number = it.size
                    }else {
                        isVisible = false
                    }
                }

        })


    }

}