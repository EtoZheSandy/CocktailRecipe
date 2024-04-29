package su.afk.cocktailrecipe.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItems (
    val title: String,
    val icon: ImageVector,
    val route: String
)

val BottomNavigationItems = listOf(
    NavItems(
        title = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),
    NavItems(
        title = "Favorite",
        icon = Icons.Default.Favorite,
        route = Screens.FavoritScreen.name
    ),
)