package com.arrazyfathan.search_presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.common_utils.dateTimeAgo
import com.arrazyfathan.common_utils.extensions.loadImage
import com.arrazyfathan.common_utils.formatHtmlText
import com.arrazyfathan.common_utils.getFirstLetterSource
import com.arrazyfathan.search_domain.model.Article
import com.arrazyfathan.search_presentation.R
import com.arrazyfathan.search_presentation.databinding.ItemArticlePreviewBinding

/**
 * Created by Ar Razy Fathan Rabbani on 16/05/23.
 */
class SearchNewsAdapter(
    private val onItemSelected: (Article) -> Unit
) : RecyclerView.Adapter<SearchNewsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemArticlePreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            with(binding) {
                ivArticleImage.loadImage(article.urlToImage)
                tvSource.text = article.source.name
                tvSourceFirstLetter.text =
                    article.source.let { getFirstLetterSource(it.name).toString() }
                tvTitle.text = article.title
                tvDescription.text = formatHtmlText(article.description)
                tvPublishedAt.text = dateTimeAgo(article.publishedAt)

                root.setOnClickListener {
                    onItemSelected(article)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticlePreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun clear() {
        differ.submitList(emptyList())
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)
}