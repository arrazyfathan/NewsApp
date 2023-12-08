package com.arrazyfathan.home_presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.common_ui.recyclerview.AdaptiveSpacingItemDecoration
import com.arrazyfathan.common_utils.extensions.dp
import com.arrazyfathan.common_utils.extensions.toJson
import com.arrazyfathan.common_utils.navigator.Navigator
import com.arrazyfathan.common_utils.navigator.Screen
import com.arrazyfathan.home_presentation.R
import com.arrazyfathan.home_presentation.TopHeadlinesViewModel
import com.arrazyfathan.home_presentation.bookmark.adapter.BookmarkAdapter
import com.arrazyfathan.home_presentation.databinding.FragmentBookmarkBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private val viewModel: TopHeadlinesViewModel by activityViewModels()

    @Inject
    lateinit var navigation: Navigator.Provider

    private lateinit var bookmarkAdapter: BookmarkAdapter

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.tvAppBar) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = insets.left
                topMargin = insets.top
                rightMargin = insets.right
            }

            WindowInsetsCompat.CONSUMED
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        setupView()
    }

    private fun setupView() = with(binding) {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        rvItemBookmark.layoutManager = layoutManager
        bookmarkAdapter = BookmarkAdapter(layoutManager) { article ->
            val extras = Bundle().apply {
                putString("article", article.toJson())
            }
            navigation.getScreen(Screen.DetailScreen).navigate(requireActivity(), extras)
        }
        rvItemBookmark.adapter = bookmarkAdapter
        rvItemBookmark.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (rvItemBookmark.canScrollVertically(-1)) {
                    // tvAppBar.cardElevation = 20f
                } else {
                    // tvAppBar.cardElevation = 0f
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })

        rvItemBookmark.addItemDecoration(AdaptiveSpacingItemDecoration(16.dp(), true))

        btnToggleLayout.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btn_layout_grid -> if (layoutManager.spanCount == 1) {
                        layoutManager.spanCount = 2
                    }

                    R.id.btn_layout_list -> if (layoutManager.spanCount == 2) {
                        layoutManager.spanCount = 1
                    }
                }
            }
            bookmarkAdapter.notifyItemRangeChanged(0, bookmarkAdapter.itemCount)
        }
    }

    private fun observe() {
        viewModel.bookmarkedArticles.observe(viewLifecycleOwner) { articles ->
            bookmarkAdapter.differ.submitList(articles)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
