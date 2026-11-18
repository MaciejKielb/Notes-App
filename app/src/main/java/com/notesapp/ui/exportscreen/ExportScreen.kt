package com.notesapp.ui.exportscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ExportScreen(
    onBackClick: () -> Unit,
    ) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Export Screen",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        TopBarExport(onBackClick = onBackClick)
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarExport(
    onBackClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = "Export Screen",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    )
}


@Preview(name = "Export Screen", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun ExportScreenPreview(){
    ExportScreen(
        onBackClick = {}
    )
}
