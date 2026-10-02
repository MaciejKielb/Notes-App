package com.notesapp.ui.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreenDrawer(
    onItemClick: (MenuItem) -> Unit,
    onCloseClick: () -> Unit
) {
    DrawerHeader(onItemClick = onCloseClick)
    DrawerBody(
        onItemClick = onItemClick,
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
        )
    )
}
@Composable
fun DrawerHeader(
    onItemClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically)
        {
            Text(
                text = "Menu",
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close drawer icon button",
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { onItemClick() },
            )
        }
    }
}

@Composable
fun DrawerBody(
    items: List<MenuItem>,
    modifier: Modifier = Modifier,
    itemTextStyle: TextStyle = TextStyle(fontSize = 16.sp),
    onItemClick: (MenuItem) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Column() {
            Row(
            ) {
                Text(
                    text = "Content",
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
            LazyColumn(modifier,) {
                items(items) { item ->
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                        .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = item.title,
                            style = itemTextStyle,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Drawer Header", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun DrawerHeaderPreview() {
    DrawerHeader(onItemClick = {})
}

@Preview(name = "DrawerBody", showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun DrawerBodyPreview() {
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
        onItemClick = {}
    )
}