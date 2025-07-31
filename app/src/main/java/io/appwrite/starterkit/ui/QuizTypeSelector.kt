package io.appwrite.starterkit.io.appwrite.starterkit.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QuizTypeSelector(
    selectedType: String,
    onSelectType: (String) -> Unit
) {
    val types = listOf("Multiple Choice", "True / False")
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        types.forEach { type ->
            AssistChip(
                onClick = { onSelectType(type) },
                label = { Text(type) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (type == selectedType) Color(0xFFF5BA69) else Color.LightGray,
                    labelColor = Color.Black
                ),
                border = null
            )
        }
    }
}
