package io.appwrite.starterkit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.starterkit.RetrofitInstance
import io.appwrite.starterkit.data.models.QuizResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {
    private val _quizResponse = MutableStateFlow<QuizResponse?>(null)
    val quizResponse: StateFlow<QuizResponse?> = _quizResponse

    fun loadQuizQuestions(
        amount: Int,
        category: Int?,
        difficulty: String?,
        type: String?
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getQuizQuestions(
                    amount = amount,
                    category = category,
                    difficulty = difficulty,
                    type = type
                )
                if (response.isSuccessful) {
                    _quizResponse.value = response.body()
                } else {
                    println("API error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                println("Error fetching questions: ${e.localizedMessage}")
            }
        }
    }
}
