package com.kani.nytimespopular.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Relation

data class ArticleWithImage(
    @Embedded val article: ArticleEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "articleId"
    )
    var images: List<ArticleImageEntity>
)
