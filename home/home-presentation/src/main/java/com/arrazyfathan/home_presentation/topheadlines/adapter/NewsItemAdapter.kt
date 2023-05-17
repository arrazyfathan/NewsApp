package com.arrazyfathan.home_presentation.topheadlines.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.common_utils.dateTimeAgo
import com.arrazyfathan.common_utils.extensions.loadImage
import com.arrazyfathan.common_utils.formatHtmlText
import com.arrazyfathan.common_utils.getFirstLetterSource
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_presentation.databinding.ItemArticlePreviewBinding

/**
 * Created by Ar Razy Fathan Rabbani on 11/05/23.
 */
class NewsItemAdapter(
    val onSelectedItem: (Article) -> Unit
) : PagingDataAdapter<Article, NewsItemAdapter.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

    inner class ViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                ivArticleImage.loadImage(article.urlToImage)
                tvSource.text = article.source.name
                tvSourceFirstLetter.text =  article.source.let { getFirstLetterSource(it.name).toString() }
                tvTitle.text = article.title
                tvDescription.text = formatHtmlText(article.description)
                tvPublishedAt.text = dateTimeAgo(article.publishedAt)

                root.setOnClickListener {
                    onSelectedItem(article)
                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }
    }
}