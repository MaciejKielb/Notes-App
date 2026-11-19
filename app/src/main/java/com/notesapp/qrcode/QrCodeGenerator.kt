package com.notesapp.qrcode

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set

fun createQrBitmap(
    content: String,
    sizePx: Int,
    paddingPx: Int = 0,
): Bitmap {
    val bitmapMatrix = try {
        QRCodeWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            sizePx,
            sizePx,
            mapOf(EncodeHintType.MARGIN to paddingPx),
        )
    } catch (ex: WriterException) {
        null
    }

    val width = bitmapMatrix?.width ?: sizePx
    val height = bitmapMatrix?.height ?: sizePx
    val bitmap = createBitmap(width, height)

    for (x in 0 until width) {
        for (y in 0 until height) {
            bitmap[x, y] = if (bitmapMatrix?.get(x, y) == true) {
                Color.BLACK
            } else {
                Color.WHITE
            }
        }
    }
    return bitmap
}

@Composable
fun qrCodeGenerator(
    content: String,
    size: Dp = 150.dp,
    padding: Dp = 0.dp,
): BitmapPainter {
    val density = LocalDensity.current
    val sizePx = with(density) { size.roundToPx() }
    val paddingPx = with(density) { padding.roundToPx() }

    var bitmap by remember(content) { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(bitmap) {
        if (bitmap != null) return@LaunchedEffect

        launch(Dispatchers.IO) {
            bitmap = createQrBitmap(content, sizePx, paddingPx)
        }
    }

    return remember(bitmap) {
        val currentBitmap = bitmap ?: createBitmap(sizePx, sizePx).apply { eraseColor(Color.TRANSPARENT) }

        BitmapPainter(currentBitmap.asImageBitmap())
    }
}
