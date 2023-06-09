package com.dicoding.submissionjetpackcompose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.submissionjetpackcompose.ui.navigation.NavigationItems
import com.dicoding.submissionjetpackcompose.ui.navigation.Route
import com.dicoding.submissionjetpackcompose.ui.screen.about.AboutMe
import com.dicoding.submissionjetpackcompose.ui.screen.detail.DetailPage
import com.dicoding.submissionjetpackcompose.ui.screen.favorite.FavoritePage
import com.dicoding.submissionjetpackcompose.ui.screen.home.HomePage
import com.dicoding.submissionjetpackcompose.ui.theme.SubmissionJetpackComposeTheme

@Composable
fun NarrativeClothesApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val thisRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (thisRoute != Route.Detail.route) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .height(55.dp)
                        .fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.logo_dark),
                        contentDescription = null,
                        modifier = Modifier
                            .height(40.dp)
                            .size(95.dp)
                    )
                }
            }
        },

        bottomBar = {
            if (thisRoute != Route.Detail.route) {
                BottomNavBar(
                    navController = navController
                )
            }
        }
    ) { paddingInner ->
        NavHost(
            navController = navController,
            startDestination = Route.Home.route,
            modifier = modifier.padding(paddingInner)
        ) {
            composable(Route.Home.route) {
                HomePage(
                    navigateToDetailPage = {idClothes ->
                        navController.navigate(Route.Detail.creatTheRoute(idClothes)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Route.Favorite.route) {
                FavoritePage(
                    navigateToDetailPage = { idClothes ->
                        navController.navigate(Route.Detail.creatTheRoute(idClothes)) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Route.About.route) {
                AboutMe()
            }

            composable(
                route = Route.Detail.route,
                arguments = listOf(navArgument("idClothes") { type = NavType.LongType})
            ) {
                val id = it.arguments?.getLong("idClothes") ?: -1L

                DetailPage(
                    idClothes = id,
                    navigateToBack = {
                        navController.navigateUp()
                    }
                )
            }
        }

    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val thisRoute = navBackStackEntry?.destination?.route

        val itemNavigation = listOf(
            NavigationItems(
                title = stringResource(id = R.string.homeMenu),
                icon = Icons.Rounded.Home,
                router = Route.Home,
                descriptionContent = stringResource(id = R.string.homePage)
            ),

            NavigationItems(
                title = stringResource(id = R.string.favoriteMenu),
                icon = Icons.Rounded.Favorite,
                router = Route.Favorite,
                descriptionContent = stringResource(id = R.string.favoritePage)
            ),

            NavigationItems(
                title = stringResource(id = R.string.aboutMeMenu),
                icon = Icons.Rounded.Face,
                router = Route.About,
                descriptionContent = stringResource(id = R.string.aboutMePage)
            )
        )

        itemNavigation.map { items ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = items.icon,
                        contentDescription = items.descriptionContent
                    )
                },
                label = {
                    Text(text = items.title)
                },
                selected = thisRoute == items.router.route,
                onClick = {
                    navController.navigate(items.router.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomOfAppPreview() {
    SubmissionJetpackComposeTheme() {
        BottomNavBar(rememberNavController())
    }
}
