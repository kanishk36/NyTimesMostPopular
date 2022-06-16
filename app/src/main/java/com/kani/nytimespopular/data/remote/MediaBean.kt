package com.kani.nytimespopular.data.remote

import com.google.gson.annotations.SerializedName
import com.kani.nytimespopular.data.local.ArticleImageEntity

data class MediaBean(
    @SerializedName("media-metadata")
    var mediaMetaData: List<ArticleImageEntity> = ArrayList()
)
