package com.kani.nytimespopular.data.remote

import com.kani.nytimespopular.data.local.ArticleEntity

data class ArticleApiResponse(val results: List<ArticleEntity>)
