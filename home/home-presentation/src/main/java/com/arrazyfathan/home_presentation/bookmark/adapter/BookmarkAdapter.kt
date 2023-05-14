package com.arrazyfathan.home_presentation.bookmark.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.common_utils.extensions.loadImage
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_presentation.databinding.ItemBookmarkLinearBinding

/**
 * Created by Ar Razy Fathan Rabbani on 14/05/23.
 */
class BookmarkAdapter(
    private val context: Context,
    private val onItemSelected: (Article) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isGrid = false

    fun setGrid(isGrid: Boolean) {
        this.isGrid = isGrid
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (isGrid) {
            val binding = ItemBookmarkLinearBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            GridViewHolder(binding)

        } else {
            val binding = ItemBookmarkLinearBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            LinearViewHolder(binding)
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        if (isGrid) {
            val gridViewHolder = holder as GridViewHolder
            gridViewHolder.bind(item)
        } else {
            val linearViewHolder = holder as LinearViewHolder
            linearViewHolder.bind(item)
        }
    }

    inner class LinearViewHolder(private val binding: ItemBookmarkLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            with(binding) {
                tvTitle.text = item.title
                tvSource.text = item.source.name
                ivArticle.loadImage(item.urlToImage)
                root.setOnClickListener {
                    onItemSelected(item)
                }
            }
        }
    }

    inner class GridViewHolder(private val binding: ItemBookmarkLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article) {
            with(binding) {

            }
        }
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