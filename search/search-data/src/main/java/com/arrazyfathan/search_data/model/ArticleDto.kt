package com.arrazyfathan.search_data.model

import com.google.gson.annotations.SerializedName


data class ArticleDto(
    @SerializedName("author")
    val author: String? = "",
    @SerializedName("content")
    val content: String? = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("publishedAt")
    val publishedAt: String? = "",
    @SerializedName("source")
    val source: SourceDto = SourceDto(),
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("urlToImage")
    val urlToImage: String? = "",
)
