package io.appwrite.starterkit.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryResponse(
    val trivia_categories: List<Category>
) : Parcelable