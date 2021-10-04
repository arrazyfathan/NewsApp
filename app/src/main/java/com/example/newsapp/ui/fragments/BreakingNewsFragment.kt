package com.example.newsapp.ui.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.newsapp.util.Resources
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var bottomNavigationBar: BottomNavigationView

    val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        bottomNavigationBar = (activity as NewsActivity).bottomNavigationView
        bottomNavigationBar.visibility = View.VISIBLE
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }

            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resources.Success -> {
                    hideProgressBar()
                    hideNoInternet()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if (isLastPage) {
                            rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resources.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                        showNoInternet()
                        Toast.makeText(activity, "An error occured: $message", Toast.LENGTH_LONG).show()
                    }
                }
                is Resources.Loading -> {
                    showProgressBar()
                }
            }
        })

        btnRetry.setOnClickListener {
            viewModel.getBreakingNews("us")
            hideNoInternet()
        }
    }

    private fun hideProgressBar() {
        loadingProgress.visibility = View.INVISIBLE
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        loadingProgress.visibility = View.VISIBLE
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun showNoInternet() {
        noInternet.visibility = View.VISIBLE
        tvNoInternet.visibility = View.VISIBLE
        btnRetry.visibility = View.VISIBLE
        rvBreakingNews.visibility = View.INVISIBLE
    }

    private fun hideNoInternet() {
        noInternet.visibility = View.INVISIBLE
        tvNoInternet.visibility = View.INVISIBLE
        btnRetry.visibility = View.INVISIBLE
        rvBreakingNews.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE

            val shouldPaginate =
                isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews("us")
                isScrolling = false
            }
        }
    }


}










