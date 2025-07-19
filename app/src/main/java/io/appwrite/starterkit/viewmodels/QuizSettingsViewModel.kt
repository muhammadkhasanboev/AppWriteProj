package io.appwrite.starterkit.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.starterkit.RetrofitInstance
import io.appwrite.starterkit.data.models.Category
import kotlinx.coroutines.launch

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
                    response.body()?.trivia_categories?.let { categoriesList ->
                        categories = categoriesList
                    } ?: run {
                        Log.e("QuizSettingsVM", "No categories in response")
                        categories = emptyList()
                    }
                } else {
                    Log.e("QuizSettingsVM", "Failed: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("QuizSettingsVM", "Error loading categories", e)
            } finally {
                isLoading = false
            }
        }
    }
}