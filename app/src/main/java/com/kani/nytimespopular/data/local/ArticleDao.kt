package com.kani.nytimespopular.data.local

import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<ArticleEntity>): LongArray

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticlesImages(images: List<ArticleImageEntity>): LongArray

    @Transaction
    @Query("SELECT * FROM 'articles'")
    fun getArticleList(): List<ArticleWithImage>

    @Query("DELETE FROM 'articles'")
    fun deleteAllArticles()

    @Query("DELETE FROM 'images'")
    fun deleteAllImages()
}