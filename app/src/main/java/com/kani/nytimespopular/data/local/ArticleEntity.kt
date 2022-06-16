package com.kani.nytimespopular.data.local

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.kani.nytimespopular.data.remote.MediaBean

@Entity(tableName = "articles",
    indices = [Index("id"), Index("title"), Index("id", "title")])
data class ArticleEntity(
    @SerializedName("id")
    @PrimaryKey
    val id: Long,

    @SerializedName("url")
    var url: String?,

    @SerializedName("asset_id")
    var assetId: Long,

    @SerializedName("published_date")
    var publishedDate: String?,

    @SerializedName("updated")
    var updatedDate: String?,

    @SerializedName("section")
    var section: String?,

    @SerializedName("subsection")
    var subSection: String?,

    @SerializedName("byline")
    var byline: String?,

    @SerializedName("title")
    var title: String?
) {
    @Ignore
    var media: List<MediaBean> = ArrayList()

    fun mapArticleIdToImageEntity() {
        val imageList: List<ArticleImageEntity> = media.get(0).mediaMetaData
        for (imageEntity in imageList) {
            imageEntity.articleId = id
        }
    }
}
