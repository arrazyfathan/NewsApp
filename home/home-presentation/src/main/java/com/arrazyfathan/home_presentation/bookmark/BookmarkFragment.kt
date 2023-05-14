package com.arrazyfathan.home_presentation.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.common_utils.Constants.TAG
import com.arrazyfathan.common_utils.extensions.observeOnce
import com.arrazyfathan.common_utils.extensions.singleShotObserve
import com.arrazyfathan.common_utils.extensions.toJson
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.common_utils.navigator.Screen
import com.arrazyfathan.home_presentation.databinding.FragmentBookmarkBinding
import com.arrazyfathan.home_presentation.TopHeadlinesViewModel
import com.arrazyfathan.home_presentation.bookmark.adapter.BookmarkAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val viewModel: TopHeadlinesViewModel by activityViewModels()

    private var _binding: FragmentBookmarkBinding? = null

    @Inject
    lateinit var navigation: Navigator.Provider
    private val binding get() = _binding!!

    private val bookmarkAdapter: BookmarkAdapter by lazy {
        BookmarkAdapter(requireContext()) { article ->
            val extras = Bundle().apply {
                putString("article", article.toJson())
            }
            navigation.getScreen(Screen.DetailScreen).navigate(requireActivity(), extras)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setupView()
    }

    private fun setupView() = with(binding) {
        rvItemBookmark.adapter = bookmarkAdapter
        rvItemBookmark.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (rvItemBookmark.canScrollVertically(-1)) {
                    tvAppBar.cardElevation = 20f
                } else {
                    tvAppBar.cardElevation = 0f
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun observe() {
        viewModel.bookmarkedArticles.singleShotObserve(viewLifecycleOwner) { articles ->
            bookmarkAdapter.differ.submitList(articles)
        }
    }
}
