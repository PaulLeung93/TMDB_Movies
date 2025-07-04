package com.example.tmdbmovies.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Represents each bottom navigation item.
 */
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

/**
 * Bottom navigation destinations
 */
val bottomNavItems = listOf(
    BottomNavItem("Home", Icons.Default.Home, "home"),
    BottomNavItem("Favorites", Icons.Default.Favorite, "favorites"),
    BottomNavItem("Settings", Icons.Default.Settings, "settings")
)

/**
 * Bottom Navigation bar to appear at the bottom of the app.
 *
 * @param currentRoute currently selected screen
 * @param onItemClick navigates to the selected screen
 */
@Composable
fun BottomNavBar(
    currentRoute: String,
    onItemClick: (BottomNavItem) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = item.route == currentRoute,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = { Text(item.label) }
            )
        }
    }
}
