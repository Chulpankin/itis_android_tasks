package com.example.itis_android_tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.model.AppDispatchers

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DispatcherSelector(
    modifier: Modifier = Modifier,
    selectedDispatcher: AppDispatchers,
    onDispatcherChange: (AppDispatchers) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text(stringResource(R.string.dispatcher))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = selectedDispatcher.name,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(
                        MenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                AppDispatchers.entries.forEach { dispatcher ->
                    DropdownMenuItem(
                        onClick = {
                            onDispatcherChange(dispatcher)
                            expanded = false
                        },
                        text = { Text(dispatcher.name) }
                    )
                }
            }
        }
    }
}