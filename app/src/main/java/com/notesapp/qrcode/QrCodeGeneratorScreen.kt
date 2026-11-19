package com.notesapp.qrcode

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CreateQrCodeGeneratorScreen(
    noteId: Long,
    viewModel: QrCodeViewModel,
    onBackClick: () -> Unit,
) {
    val payload by viewModel.payload.collectAsStateWithLifecycle()
    val isTooLong by viewModel.isTooLong.collectAsStateWithLifecycle()

    LaunchedEffect(noteId) {
        viewModel.load(noteId)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        when {
            isTooLong -> {
                Text(text = "This note is too long to share as a QR code.")
            }
            payload != null -> {
                Image(
                    painter = qrCodeGenerator(payload.orEmpty()),
                    contentDescription = "QR code",
                    modifier = Modifier.size(150.dp),
                )
            }
        }
    }
}
