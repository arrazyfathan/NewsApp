package com.arrazyfathan.home_presentation.topheadlines

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.arrazyfathan.common_utils.extensions.toJson
import com.arrazyfathan.common_utils.extensions.toast
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.common_utils.navigator.Screen
import com.arrazyfathan.home_data.NoInternetException
import com.arrazyfathan.home_presentation.databinding.FragmentTopHeadlinesBinding
import com.arrazyfathan.home_presentation.topheadlines.adapter.NewsItemAdapter
import com.arrazyfathan.home_presentation.topheadlines.adapter.PagingLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TopHeadlinesFragment : Fragment() {

    @Inject
    lateinit var navigation: Navigator.Provider

    private val viewModel: TopHeadlinesViewModel by activityViewModels()
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(requireContext())
    }

    private val topHeadlinesAdapter: NewsItemAdapter by lazy {
        NewsItemAdapter { article ->
            val extras = Bundle().apply {
                putString("article", article.toJson())
            }
            navigation.getScreen(Screen.DetailScreen).navigate(requireActivity(), extras)
        }
    }

    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        observe()
    }

    private fun setupView() = with(binding) {
        with(topHeadlinesAdapter) {
            rvBreakingNews.adapter = withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter(this),
                footer = PagingLoadStateAdapter(this)
            )
            rvBreakingNews.layoutManager = linearLayoutManager

            lifecycleScope.launch {
                topHeadlinesAdapter.loadStateFlow.collect { loadState ->
                    val isListEmpty =
                        loadState.refresh is LoadState.NotLoading && topHeadlinesAdapter.itemCount == 0
                    // show empty list

                    // Only show the list if refresh succeeds, either from the the local db or the remote.
                    binding.rvBreakingNews.isVisible =
                        loadState.source.refresh is LoadState.NotLoading // || loadState.mediator?.refresh is LoadState.NotLoading

                    // Show loading spinner during initial load or refresh.
                    binding.loadingProgress.isVisible = loadState.refresh is LoadState.Loading
                    binding.swipe.isRefreshing = false

                    // Show the retry state if initial load or refresh fails.
                    val error = loadState.refresh is LoadState.Error
                    binding.btnRetry.isVisible = error
                    binding.tvNoInternet.isVisible = error
                    binding.noInternet.isVisible = error

                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        toast("\uD83D\uDE28 Wooops, ${it.error.message}")
                        binding.tvNoInternet.text = "\uD83D\uDE28 Wooops, ${it.error.message}"
                    }
                }
            }
        }
        rvBreakingNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                val itemCount = linearLayoutManager.itemCount
                if (firstVisibleItemPosition != RecyclerView.NO_POSITION) {
                    lifecycleScope.launch {
                        delay(500L)
                        viewModel.lastFirstVisiblePosition = firstVisibleItemPosition
                    }
                }

                if (lastVisibleItemPosition == itemCount - 1) {
                    revealButtonBackTopTop()
                }

                if (dy < 0) {
                    hideButtonToTop()
                }

                if (binding.rvBreakingNews.canScrollVertically(-1)) {
                    binding.tvAppBar.cardElevation = 20f
                } else {
                    binding.tvAppBar.cardElevation = 0f
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        btnRetry.setOnClickListener {
            topHeadlinesAdapter.retry()
        }

        swipe.setOnRefreshListener {
            topHeadlinesAdapter.refresh()
        }

        btnBackToTop.setOnClickListener {
            rvBreakingNews.smoothScrollToPosition(0)
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.topHeadlinesPager.collectLatest(topHeadlinesAdapter::submitData)
            }
        }
    }

    private fun revealButtonBackTopTop() {
        val transition = Slide(Gravity.BOTTOM)
        transition.apply {
            duration = 300
            addTarget(binding.btnBackToTop)
            interpolator = LinearInterpolator()
        }
        TransitionManager.beginDelayedTransition(binding.root, transition)
        binding.btnBackToTop.isVisible = true
    }

    private fun hideButtonToTop() {
        val transition = Slide(Gravity.BOTTOM)
        transition.apply {
            duration = 300
            addTarget(binding.btnBackToTop)
            interpolator = LinearInterpolator()
        }
        TransitionManager.beginDelayedTransition(binding.root, transition)
        binding.btnBackToTop.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.lastFirstVisiblePosition != RecyclerView.NO_POSITION) {
            binding.rvBreakingNews.scrollToPosition(viewModel.lastFirstVisiblePosition)
        }
    }
}

