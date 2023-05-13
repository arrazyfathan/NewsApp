package com.arrazyfathan.detail_presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.arrazyfathan.common_utils.Constants.TAG
import com.arrazyfathan.common_utils.dateTimeAgo
import com.arrazyfathan.common_utils.extensions.fromJson
import com.arrazyfathan.common_utils.extensions.showCustomToast
import com.arrazyfathan.common_utils.extensions.singleShotObserve
import com.arrazyfathan.common_utils.getFirstLetterSource
import com.arrazyfathan.detail_presentation.databinding.ActivityDetailArticleBinding
import com.arrazyfathan.home_domain.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private val viewModel: DetailArticleViewModel by viewModels()

    private val article: Article? by lazy {
        intent.getStringExtra("article")?.fromJson()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        observe()
    }

    private fun observe() {
        article?.let {
            viewModel.checkArticleIsBookmarked(it.title).observe(this) { state ->
                binding.fab.isVisible = !state
            }
        }
    }

    private fun setupView() = with(binding) {
        binding.loadingWebView.visibility = View.VISIBLE
        article?.let { article ->
            tvSourceArticle.text = article.source.name
            tvPublishedAtArticle.text = dateTimeAgo(article.publishedAt)
            tvSourceLetterArticle.text = getFirstLetterSource(article.source.name).toString()
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
                settings.userAgentString = "Android"
            }

            webView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (scrollY > oldScrollY && scrollY > 0) {
                    topBarDetail.cardElevation = 20f
                }
                if (scrollY == 0) {
                    topBarDetail.cardElevation = 0f
                }
            }

            setupProgressBar()

            fab.setOnClickListener {
                viewModel.bookmarkArticle(article)
                showCustomToast("Article Bookmarked", this@DetailArticleActivity)
            }
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
                    binding.loadingWebView.visibility = View.GONE
                } catch (e: NullPointerException) {
                    Log.d(TAG, e.message.toString())
                }
            }
        }
    }

    companion object {
        fun launchActivity(activity: Activity, extras: Bundle?) {
            val intent = Intent(activity, DetailArticleActivity::class.java)
            extras?.let {
                intent.putExtras(it)
            }
            activity.startActivity(intent)
        }
    }
}