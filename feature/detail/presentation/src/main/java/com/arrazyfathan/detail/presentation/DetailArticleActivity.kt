package com.arrazyfathan.detail.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.webkit.WebView
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import androidx.lifecycle.lifecycleScope
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.arrazyfathan.common.utils.Constants.TAG
import com.arrazyfathan.common.utils.dateTimeAgo
import com.arrazyfathan.common.utils.extensions.fromJson
import com.arrazyfathan.common.utils.extensions.setOnOneTimeClickListener
import com.arrazyfathan.common.utils.extensions.showCustomToast
import com.arrazyfathan.common.utils.extensions.singleShotObserve
import com.arrazyfathan.common.utils.getFirstLetterSource
import com.arrazyfathan.detail.presentation.databinding.ActivityDetailArticleBinding
import com.arrazyfathan.home.domain.model.Article
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailArticleBinding
    private val viewModel: DetailArticleViewModel by viewModels()

    private val article: Article? by lazy {
        intent.getStringExtra("article")?.fromJson()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(
                WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.displayCutout()
            )
            binding.topBarDetail.updatePadding(
                left = insets.left,
                top = insets.top,
                right = insets.right
            )
            windowInsets
        }

        setupView()
        observe()
    }

    private fun observe() {
        article?.let {
            viewModel.checkArticleIsBookmarked(it.title).observe(this) { state ->
                if (it.isBookmarked) binding.btnBookmark.isVisible = false
                if (state) {
                    lifecycleScope.launch {
                        delay(600L)
                        hideButtonBookmark()
                    }
                }
            }
        }
    }

    private fun setupView() = with(binding) {
        binding.loadingWebView.visibility = View.VISIBLE
        article?.let { article ->
            tvSourceArticle.text = article.source.name
            tvPublishedAtArticle.text = dateTimeAgo(article.publishedAt)
            tvSourceLetterArticle.text = getFirstLetterSource(article.source.name.orEmpty()).toString()
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
                settings.userAgentString = "Android"
            }

            webView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                val targetElevation = if (scrollY > 0) 20f else 0f
                if (topBarDetail.elevation != targetElevation) {
                    topBarDetail.elevation = targetElevation
                }
            }

            setupProgressBar()

            btnBookmark.setOnOneTimeClickListener {
                animeBookmark.apply {
                    setMaxFrame(50)
                    playAnimation()
                    bookmarkArticle(article)
                    showCustomToast("Article Bookmarked", this@DetailArticleActivity)
                }
            }
        }
    }

    private fun bookmarkArticle(article: Article) {
        viewModel.bookmarkArticle(article)
    }

    private fun hideButtonBookmark() {
        val transition = Slide(Gravity.BOTTOM)
        transition.apply {
            duration = 500
            addTarget(binding.btnBookmark)
            interpolator = AnticipateInterpolator()
        }
        TransitionManager.beginDelayedTransition(binding.root, transition)
        binding.btnBookmark.visibility = View.INVISIBLE
    }

    private fun setupProgressBar() {
        binding.webView.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
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
