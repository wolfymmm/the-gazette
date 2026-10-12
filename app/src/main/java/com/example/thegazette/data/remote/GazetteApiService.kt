package com.example.thegazette.data.remote

import com.example.thegazette.data.model.ArticleDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GazetteApiService {
    @GET("api/v1/articles")
    suspend fun getArticles(
        @Query("category") category: String? = null
    ): List<ArticleDto>

    @GET("api/v1/articles/{id}")
    suspend fun getArticleDetails(
        @Path("id") articleId: Int
    ): ArticleDto
}