package com.notesapp.ui.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.notesapp.TopBar.DrawerBody
import com.notesapp.TopBar.DrawerHeader
import com.notesapp.TopBar.MenuItem
import com.notesapp.TopBar.TopAppBar
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(
                    items = listOf(
                        MenuItem(
                            id = "QR Scanner",
                            title = "QR Scanner",
                            contentDescription = "Open QR Scanner",
                            icon = Icons.Default.AddCircle,
                        ),
                        MenuItem(
                            id = "App Version",
                            title = "App Version",
                            contentDescription = "App Version",
                            icon = Icons.Default.Info,
                        ),
                    ),
                    onItemClick = {
                        when (it.id) {
                            "QR Scanner" -> println("Clicked on QR Scanner")
                            "App Version" -> println("Clicked on App Version")
                        }
                    },
                )
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    onNavigationIconClick = {
                        scope.launch { drawerState.open() }
                    },
                )
            },
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Notes",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview(name = "Main Screen", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun PreviewMainScreen() {
    MainScreen()
}