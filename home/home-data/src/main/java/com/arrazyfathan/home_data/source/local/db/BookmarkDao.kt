package com.arrazyfathan.home_data.source.local.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arrazyfathan.home_data.source.local.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM article")
    fun getBookmarkedArticle(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertArticle(articleEntity: ArticleEntity)

    @Delete
    fun deleteArticle(articleEntity: ArticleEntity)

    @Query("SELECT EXISTS (SELECT * FROM article WHERE title = :title)")
    fun checkArticleIsBookmarked(title: String): Flow<Boolean>
}