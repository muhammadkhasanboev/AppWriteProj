package io.appwrite.starterkit.io.appwrite.starterkit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.appwrite.starterkit.data.models.Category

@Composable
fun DifficultySelector(
    selectedDifficulty: String,
    onSelectDifficulty: (String) -> Unit
) {
    var selectedDifficulty by remember { mutableStateOf<Category?>(null) }
    val difficulties = listOf(
        Category(1, "Easy"),
        Category(2, "Medium"),
        Category(3, "Hard")
    )
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        difficulties.forEach { difficulty ->
            AssistChip(
                onClick = { onSelectDifficulty(difficulty) },
                label = { Text(difficulty) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (difficulty == selectedDifficulty) Color(0xFFF5BA69) else Color.LightGray,
                    labelColor = Color.Black
                ),
                border = null
            )
        }
    }
}
