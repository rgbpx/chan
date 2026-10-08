package com.github.rgbpx.chan.imageboard.site.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.rgbpx.chan.imageboard.engine.EngineId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiteForm(
    name: String,
    baseUrl: String,
    engineId: EngineId,
    engineIds: List<EngineId>,
    onNameChanged: (String) -> Unit,
    onBaseUrlChanged: (String) -> Unit,
    onEngineSelected: (EngineId) -> Unit,
    onSaveClicked: () -> Unit,
    onCancelClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(24.dp),
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChanged,
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = baseUrl,
            onValueChange = onBaseUrlChanged,
            label = { Text("Base URL") },
            placeholder = {
                Text("https://example.com")
            },
            modifier = Modifier.fillMaxWidth(),
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            },
        ) {
            OutlinedTextField(
                value = engineId.value,
                onValueChange = {},
                readOnly = true,
                label = { Text("Engine") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded,
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(
                        ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                    ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                engineIds.forEach { engineId ->
                    DropdownMenuItem(
                        text = {
                            Text(engineId.value)
                        },
                        onClick = {
                            expanded = false
                            onEngineSelected(engineId)
                        },
                    )
                }
            }
        }

        Button(
            onClick = onSaveClicked,
            enabled = name.isNotBlank() && baseUrl.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            Text("Save")
        }

        OutlinedButton(
            onClick = onCancelClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Text("Cancel")
        }
    }
}
