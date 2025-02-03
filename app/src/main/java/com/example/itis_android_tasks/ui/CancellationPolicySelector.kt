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
import com.example.itis_android_tasks.model.CancellationPolicy

@Composable
fun CancellationPolicySelector(
    modifier: Modifier = Modifier,
    cancellationPolicy: CancellationPolicy,
    onCancellationPolicyChange: (CancellationPolicy) -> Unit
) {
    Column(modifier = modifier) {
        Text(stringResource(R.string.cancellation_policy))
        CancellationPolicy.entries.forEach { policy ->
            Row(
                modifier = Modifier.selectable(
                    selected = (cancellationPolicy == policy),
                    onClick = { onCancellationPolicyChange(policy) }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (cancellationPolicy == policy),
                    onClick = { onCancellationPolicyChange(policy) }
                )
                Text(text = policy.name)
            }
        }
    }
}