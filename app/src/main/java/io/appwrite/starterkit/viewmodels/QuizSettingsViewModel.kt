package io.appwrite.starterkit.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import io.appwrite.starterkit.data.models.Category
import kotlinx.coroutines.launch
import io.appwrite.starterkit.RetrofitInstance

class QuizSettingsViewModel : ViewModel() {
    var categories by mutableStateOf<List<Category>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getCategories()
                if (response.isSuccessful) {
                    categories = response.body()?.trivia_categories ?: emptyList()
                } else {
                    Log.e("QuizSettingsVM", "Failed: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("QuizSettingsVM", "Error loading categories", e)
            } finally {
                isLoading = false
            }
        }
    }
}
