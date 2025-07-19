package io.appwrite.starterkit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.services.Databases
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val client = Client(application)
        .setEndpoint("https://fra.cloud.appwrite.io/v1") // Replace this
        .setProject("686f662d00384d0a13b9")                    // Replace this

    private val databases = Databases(client)

    fun storeScore(username: String, score: Int) {
        viewModelScope.launch {
            try {
                val result = databases.createDocument(
                    databaseId = "686f8fe800255dd77fbe",
                    collectionId = "686f8ff1003547702783",
                    documentId = "unique()",
                    data = mapOf(
                        "username" to username,
                        "score" to score
                    )
                )
                println("✅ Score stored: ${result.data}")
            } catch (e: Exception) {
                e.printStackTrace()
                println("❌ Failed to store score: ${e.message}")
            }
        }
    }
}
