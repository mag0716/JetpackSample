package com.github.mag0716.composesamples.ui.androiddevchallenge3

import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

sealed class BottomNavigationMenu(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavigationMenu("home", "Home", Icons.Filled.Home)
    object Favorites : BottomNavigationMenu("favorites", "Favorites", Icons.Filled.FavoriteBorder)
    object Profile : BottomNavigationMenu("profile", "Profile", Icons.Filled.AccountCircle)
    object Cart : BottomNavigationMenu("Cart", "Cart", Icons.Filled.ShoppingCart)
}

val bottomNavigationMenus = listOf(
    BottomNavigationMenu.Home,
    BottomNavigationMenu.Favorites,
    BottomNavigationMenu.Profile,
    BottomNavigationMenu.Cart,
)

@Composable
fun BottomNavigationScreen() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colors.primary
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                bottomNavigationMenus.forEach { menu ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                menu.icon,
                                contentDescription = menu.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                menu.title,
                                style = MaterialTheme.typography.caption
                            )
                        },
                        selected = navBackStackEntry?.destination?.route == menu.route,
                        onClick = {
                            navController.navigate(menu.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId)
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = BottomNavigationMenu.Home.route) {
            composable(BottomNavigationMenu.Home.route) { HomeScreen() }
            composable(BottomNavigationMenu.Favorites.route) {}
            composable(BottomNavigationMenu.Profile.route) {}
            composable(BottomNavigationMenu.Cart.route) {}
        }
    }
}