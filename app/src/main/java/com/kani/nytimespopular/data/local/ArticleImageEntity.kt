package com.kani.nytimespopular.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
data class ArticleImageEntity(
    @PrimaryKey
    var url: String,
    var format: String?,
    var height: Int?,
    var width: Int?,
    var articleId: Long = 0
)