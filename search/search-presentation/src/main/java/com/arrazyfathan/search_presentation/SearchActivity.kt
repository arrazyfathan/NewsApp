package com.arrazyfathan.search_presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.common_ui.recyclerview.AdaptiveSpacingItemDecoration
import com.arrazyfathan.common_utils.Constants
import com.arrazyfathan.common_utils.extensions.dp
import com.arrazyfathan.common_utils.extensions.hideKeyboard
import com.arrazyfathan.common_utils.extensions.showKeyboard
import com.arrazyfathan.common_utils.extensions.toJson
import com.arrazyfathan.common_utils.extensions.toast
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.common_utils.navigator.Screen
import com.arrazyfathan.search_presentation.adapter.SearchNewsAdapter
import com.arrazyfathan.search_presentation.databinding.ActivitySearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges
import javax.inject.Inject


@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    @Inject
    lateinit var navigation: Navigator.Provider

    private val viewModel: SearchViewModel by viewModels()

    private val adapter: SearchNewsAdapter by lazy {
        SearchNewsAdapter { article ->
            val extras = Bundle().apply {
                putString("article", article.toJson())
            }
            navigation.getScreen(Screen.DetailScreen).navigate(this, extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        observe()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.searchArticles.collectLatest { state ->
                if (state.isLoading) {
                    binding.progressBar.visibility = View.VISIBLE
                } else {
                    binding.progressBar.visibility = View.GONE
                }

                if (state.error.isNotBlank()) {
                    toast(state.error)
                    binding.rvSearchList.isVisible = false
                    binding.tvError.isVisible = true
                    binding.tvError.text = state.error
                } else {
                    binding.tvError.isVisible = false
                }

                state.data?.let {  articles ->
                    binding.rvSearchList.isVisible = true
                    adapter.differ.submitList(articles)

                    if (articles.isEmpty()) {
                        binding.tvError.isVisible = true
                        binding.tvError.text = "No articles found"
                    } else {
                        binding.tvError.isVisible = false
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @OptIn(FlowPreview::class)
    private fun setupView() = with(binding) {
        showKeyboard(this@SearchActivity, editTextSearch)
        rvSearchList.adapter = adapter


        val queryMap = mutableMapOf<String, String>()
        queryMap["apiKey"] = Constants.API_KEY

        editTextSearch.textChanges()
            .skipInitialValue()
            .debounce(1000L)
            .onEach { query ->
                if (query.isNotBlank() && query.length > 3) {
                    queryMap[Constants.QUERY] = query.toString()
                    adapter.clear()
                    viewModel.getSearchNewsArticles(queryMap)
                }
            }
            .launchIn(lifecycleScope)

        rvSearchList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (binding.rvSearchList.canScrollVertically(-1)) {
                    binding.searchBarContainer.cardElevation = 20f
                } else {
                    binding.searchBarContainer.cardElevation = 0f
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        rvSearchList.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
    }

    companion object {
        fun launchActivity(activity: Activity) {
            activity.startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}
