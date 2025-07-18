package io.appwrite.starterkit.data.models

data class CategoryResponse(
    val trivia_categories: List<Category>
)

data class Category(
    val id: Int,
    val name: String
)
