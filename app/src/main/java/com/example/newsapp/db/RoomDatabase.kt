package com.example.newsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arrazyfathan.home_data.source.local.db.BookmarkDao
import com.arrazyfathan.home_data.source.local.entities.ArticleEntity

/**
 * Created by Ar Razy Fathan Rabbani on 13/05/23.
 */

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getBookmarkDao(): BookmarkDao

    companion object {
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "newsapp.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}