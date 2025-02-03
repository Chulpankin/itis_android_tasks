package com.example.itis_android_tasks.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.model.ExecutionMode

@Composable
fun ExecutionModeSelector(
    modifier: Modifier,
    executionMode: ExecutionMode,
    onExecutionModeChange: (ExecutionMode) -> Unit
) {
    Column(modifier = modifier) {
        Text(stringResource(R.string.execution_mode))
        ExecutionMode.entries.forEach { mode ->
            Row(
                modifier = Modifier.selectable(
                    selected = (executionMode == mode),
                    onClick = { onExecutionModeChange(mode) }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (executionMode == mode),
                    onClick = { onExecutionModeChange(mode) }
                )
                Text(text = mode.name)
            }
        }
    }
}