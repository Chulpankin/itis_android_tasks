package com.example.itis_android_tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.itis_android_tasks.R
import com.example.itis_android_tasks.model.*

@Composable
fun CoroutineSimulatorScreen(
    onStartCoroutines: (Int, ExecutionMode, CancellationPolicy, AppDispatchers) -> Unit,
    onCancelCoroutines: () -> Unit,
    isRunning: Boolean
) {
    var numberOfCoroutines by remember { mutableStateOf("") }
    var executionMode by remember { mutableStateOf(ExecutionMode.SEQUENTIAL) }
    var cancellationPolicy by remember { mutableStateOf(CancellationPolicy.CONTINUE_ON_PAUSE) }
    var selectedDispatcher by remember { mutableStateOf(AppDispatchers.DEFAULT) }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = numberOfCoroutines,
            onValueChange = {
                numberOfCoroutines = it
                isError = numberOfCoroutines.toIntOrNull() == null || numberOfCoroutines.toInt() <= 0
            },
            label = { Text("Number of Coroutines") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExecutionModeSelector(
            modifier = Modifier.fillMaxWidth(),
            executionMode = executionMode,
            onExecutionModeChange = { executionMode = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        CancellationPolicySelector(
            modifier = Modifier.fillMaxWidth(),
            cancellationPolicy = cancellationPolicy,
            onCancellationPolicyChange = { cancellationPolicy = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DispatcherSelector(
            modifier = Modifier.fillMaxWidth(),
            selectedDispatcher = selectedDispatcher,
            onDispatcherChange = { selectedDispatcher = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = !isError && numberOfCoroutines.toIntOrNull() != null,
            onClick = {
                onStartCoroutines(
                    numberOfCoroutines.toInt(),
                    executionMode,
                    cancellationPolicy,
                    selectedDispatcher
                )
            },
            content = { Text(text = stringResource(R.string.start)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            enabled = isRunning,
            onClick = onCancelCoroutines,
            content = { Text(text = stringResource(R.string.stop)) }
        )
    }
}