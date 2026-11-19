package com.notesapp.qrcode

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun QrCodeScannerScreen(
    onBackClick: () -> Unit,
) {
    var code by remember { mutableStateOf("") }
    var surfaceRequest by remember { mutableStateOf<SurfaceRequest?>(null) }

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
    val preview = remember {
        Preview.Builder().build().apply {
            setSurfaceProvider { request -> surfaceRequest = request }
        }
    }
    val selector = remember {
        CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }
    val imageAnalysis = remember {
        ImageAnalysis.Builder()
            .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .apply {
                setAnalyzer(
                    ContextCompat.getMainExecutor(context),
                    QrCodeAnalyzer { result ->
                        code = result
                    }
                )
            }
    }

    LaunchedEffect(true) {
        launcher.launch(Manifest.permission.CAMERA)
    }

    LaunchedEffect(hasCameraPermissions) {
        if (!hasCameraPermissions) return@LaunchedEffect
        cameraProviderFuture.addListener(
            {
                val cameraProvider = cameraProviderFuture.get()
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    preview,
                    imageAnalysis,
                )
            },
            ContextCompat.getMainExecutor(context),
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
        ) {
        if (hasCameraPermissions) {
            surfaceRequest?.let { request ->
                CameraXViewfinder(
                    surfaceRequest = request,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
        QrTopBar(
            onBackClick = onBackClick
        )
    }
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