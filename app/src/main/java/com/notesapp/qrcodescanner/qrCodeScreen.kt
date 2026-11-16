package com.notesapp.qrcodescanner

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun QrCodeScreen(
    onBackClick: () -> Unit,
) {
    var code by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context)
    }
    var hasCameraPermissions by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermissions = isGranted
        }
    )
    LaunchedEffect(true) {
        launcher.launch(Manifest.permission.CAMERA)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
        ) {
        if (hasCameraPermissions) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    val previewView = PreviewView(context)
                    val preview = Preview.Builder().build()
                    val selector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()
                    preview.surfaceProvider = previewView.surfaceProvider
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    imageAnalysis.setAnalyzer(
                        ContextCompat.getMainExecutor(context),
                        qrCodeAnalyzer { result ->
                            code = result
                        }
                    )
                    cameraProviderFuture.addListener(
                        {
                            try {
                                cameraProviderFuture.get().bindToLifecycle(
                                    lifecycleOwner,
                                    selector,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        },
                        ContextCompat.getMainExecutor(context)
                    )
                    previewView.implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    previewView
                },
            )
        }
    }
    QrTopBar(
        onBackClick = onBackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrTopBar(
    onBackClick: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
        ),
        title = {
            Text(
                text = "QR Code Scanner",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        }
    )
}

@androidx.compose.ui.tooling.preview.Preview(name = "QR Code Top Bar", showBackground = false, widthDp = 390)
@Composable
fun QrTopBarPreview() {
    QrTopBar(
        onBackClick = {}
    )
}