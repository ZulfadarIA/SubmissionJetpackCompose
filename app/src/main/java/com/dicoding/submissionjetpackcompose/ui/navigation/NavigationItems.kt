package com.dicoding.submissionjetpackcompose.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItems(
    val title: String,
    val icon: ImageVector,
    val router: Route,
    val descriptionContent: String
)
