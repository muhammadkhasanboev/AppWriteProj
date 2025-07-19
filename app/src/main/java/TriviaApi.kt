package io.appwrite.starterkit

import io.appwrite.starterkit.data.models.CategoryResponse
import io.appwrite.starterkit.data.models.QuizResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApi {
    @GET("api.php")
    suspend fun getQuizQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int?,
        @Query("difficulty") difficulty: String?,
        @Query("type") type: String?
    ): Response<QuizResponse>

    @GET("api_category.php")
    suspend fun getCategories(): Response<CategoryResponse>
}