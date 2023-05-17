package com.arrazyfathan.home_presentation.bookmark.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arrazyfathan.common_utils.extensions.loadImage
import com.arrazyfathan.home_domain.model.Article
import com.arrazyfathan.home_presentation.databinding.ItemBookmarkGridBinding
import com.arrazyfathan.home_presentation.databinding.ItemBookmarkLinearBinding


/**
 * Created by Ar Razy Fathan Rabbani on 14/05/23.
 */
class BookmarkAdapter(
    private val layoutManager: GridLayoutManager? = null,
    private val onItemSelected: (Article) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    enum class ViewType {
        GRID,
        LINEAR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.GRID.ordinal -> GridViewHolder(parent)
            else -> LinearViewHolder(parent)
        }
    }

    override fun getItemCount() = differ.currentList.size

    override fun getItemViewType(position: Int): Int {
        return if (layoutManager?.spanCount == 1) ViewType.LINEAR.ordinal else ViewType.GRID.ordinal
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = differ.currentList[position]
        when (holder) {
            is GridViewHolder -> {
                holder.bind(item)
            }

            is LinearViewHolder -> {
                holder.bind(item)
            }
        }
    }

    inner class LinearViewHolder(private val binding: ItemBookmarkLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        constructor(parent: ViewGroup) : this(
            ItemBookmarkLinearBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

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

    inner class GridViewHolder(private val binding: ItemBookmarkGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        constructor(parent: ViewGroup) : this(
            ItemBookmarkGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )

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