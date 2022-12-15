package com.example.newsapp.ui.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleBinding
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment() {

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var bottomNavigationBar: BottomNavigationView
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    val TAG = "BookmarkedFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        bottomNavigationBar = (activity as NewsActivity).findViewById(R.id.bottomNavigationView)
        bottomNavigationBar.visibility = View.INVISIBLE

        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
            settings.userAgentString = "Android"
        }
        setupProgressBar()

        if (article.id == null) {
            binding.fab.visibility = View.VISIBLE
            binding.webView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY && scrollY > 0) {
                    binding.fab.hide()
                }
                if (scrollY < oldScrollY) {
                    binding.fab.show()
                }
            }
        } else {
            binding.fab.visibility = View.INVISIBLE
        }

        binding.tvSourceArticle.text = article.source?.name
        binding.tvPublishedAtArticle.text = article.publishedAt?.let { Utils.dateTimeAgo(it) }
        binding.tvSourceLetterArticle.text =
            article.source?.let { Utils.getFirstLetterSource(it.name).toString() }

        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setupProgressBar() {
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.loadingWebView.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                try {
                    binding.loadingWebView.visibility = View.INVISIBLE
                } catch (e: NullPointerException) {
                    Log.d(TAG, e.message.toString())
                }
            }
        }
    }
}
