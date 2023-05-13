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
import com.arrazyfathan.common_utils.Constants.TAG
import com.arrazyfathan.common_utils.extensions.observeOnce
import com.arrazyfathan.common_utils.extensions.singleShotObserve
import com.arrazyfathan.home_presentation.databinding.FragmentBookmarkBinding
import com.arrazyfathan.home_presentation.TopHeadlinesViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookmarkFragment : Fragment() {

    private val viewModel: TopHeadlinesViewModel by activityViewModels()

    private var _binding: FragmentBookmarkBinding? = null
    private val binding get() = _binding!!

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
    }

    private fun observe() {
        viewModel.bookmarkedArticles.singleShotObserve(viewLifecycleOwner) {
            Log.d(TAG, it.toString())
        }
    }
}
