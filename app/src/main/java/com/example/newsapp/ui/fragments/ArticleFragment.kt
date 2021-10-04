package com.example.newsapp.ui.fragments

import android.graphics.Bitmap
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.NewsViewModel
import com.example.newsapp.util.Utils
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: NewsViewModel
    val args: ArticleFragmentArgs by navArgs()
    lateinit var bottomNavigationBar: BottomNavigationView

    val TAG = "BookmarkedFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        bottomNavigationBar = (activity as NewsActivity).bottomNavigationView
        bottomNavigationBar.visibility = View.INVISIBLE

        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
            settings.userAgentString = "Android"
        }
        setupProgressBar()

        if (article.id == null) {
            fab.visibility = View.VISIBLE
            webView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY && scrollY > 0) {
                    fab.hide()
                }
                if (scrollY < oldScrollY) {
                    fab.show()
                }
            }
        } else {
            fab.visibility = View.INVISIBLE
        }

        tvSourceArticle.text = article.source?.name
        tvPublishedAtArticle.text = article.publishedAt?.let { Utils.dateTimeAgo(it) }
        tvSourceLetterArticle.text =
            article.source?.let { Utils.getFirstLetterSource(it.name).toString() }


        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }


    }

    private fun setupProgressBar() {
        webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                return false
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingWebView.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                try {
                    loadingWebView.visibility = View.INVISIBLE
                } catch (e: NullPointerException) {
                    Log.d(TAG, e.message.toString())
                }
            }
        }
    }

}