package com.robert.ghibliapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun MovieDetailsTopBar(
    title: String,
    isFavorite: Boolean,
    onFavoriteClicked: () -> Unit,
    upPressed: () -> Unit
) {
    TopAppBar(
//        backgroundColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
//        contentColor = androidx.compose.material3.MaterialTheme.colorScheme.onBackground,
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = upPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back to Home"
                )
            }
        },
        actions = {
//            if (isFavoriteLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .size(24.dp),
//                    color = MaterialTheme.colorScheme.primary,
//                    strokeWidth = 2.dp
//                )
//            } else {
            IconButton(
                onClick = onFavoriteClicked,
                content = {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        tint = Color.Red
                    )
                }
            )
//            }
        }
    )
}