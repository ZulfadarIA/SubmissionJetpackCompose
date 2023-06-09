package com.dicoding.submissionjetpackcompose.ui.navigation

sealed class Route(val route: String) {
    object Home : Route("home")
    object About : Route("aboutme")
    object Favorite : Route("favorite")
    object  Detail : Route("home/{idClothes}") {
        fun creatTheRoute(idClothes: Long) = "home/$idClothes"
    }
}