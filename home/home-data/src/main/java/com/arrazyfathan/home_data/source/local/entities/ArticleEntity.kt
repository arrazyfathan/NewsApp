package com.arrazyfathan.home_data.source.local.entities

import androidx.annotation.NonNull
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.arrazyfathan.home_data.model.SourceDto
import com.google.gson.annotations.SerializedName

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey
    val title: String = "",
    val author: String? = "",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String? = "",
    @Embedded
    val source: SourceEntity = SourceEntity(),
    val url: String? = "",
    val urlToImage: String? = "",
    val isBookmarked: Boolean = false
)