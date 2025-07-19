package io.appwrite.starterkit.data.models
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuizResponse(
    val response_code: Int,
    val results: List<Question>
) : android.os.Parcelable

@Parcelize
data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
) : android.os.Parcelable