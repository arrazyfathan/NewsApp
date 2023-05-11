package com.arrazyfathan.home_presentation.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.arrazyfathan.home_presentation.databinding.FragmentBookmarkBinding
import com.arrazyfathan.home_presentation.topheadlines.TopHeadlinesViewModel

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
}
