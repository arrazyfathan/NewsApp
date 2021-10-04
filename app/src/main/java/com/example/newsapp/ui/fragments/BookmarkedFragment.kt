package com.example.newsapp.ui.fragments

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_bookmarked.*
import kotlinx.android.synthetic.main.fragment_search_news.*

class BookmarkedFragment : Fragment(R.layout.fragment_bookmarked) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var bottomNavigationBar: BottomNavigationView
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")


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
                R.id.action_bookmarkedFragment_to_articleFragment,
                bundle
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                val deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_delete)!!
                val intrinsicWidth = deleteIcon.intrinsicWidth + 30
                val intrinsicHeight = deleteIcon.intrinsicHeight + 30

                val itemView = viewHolder.itemView
                val itemHeight = itemView.bottom - itemView.top

                //draw red delete
                background.color = backgroundColor
                background.setBounds(
                    itemView.right + dX.toInt(),
                    itemView.top,
                    itemView.right,
                    itemView.bottom
                )
                background.draw(c)

                //calculate position of delete icon
                val iconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
                val iconMargin = (itemHeight - intrinsicHeight) / 2
                val iconLeft = itemView.right - iconMargin - intrinsicWidth
                val iconRight = itemView.right - iconMargin
                val iconBottom = iconTop + intrinsicHeight

                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                deleteIcon.draw(c)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteSavedNews(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    anchorView = bottomNavigationBar
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvBookmarkedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)

        })
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        rvBookmarkedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}