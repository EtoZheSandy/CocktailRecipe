package su.afk.cocktailrecipe.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import su.afk.cocktailrecipe.R

data class NavItems (
    val title: String,
    val iconSelected: Int,
    val iconUnselected: Int,
    val route: String
)

val BottomNavigationItems = listOf(
    NavItems(
        title = "Home",
        iconSelected  = R.drawable.icon_cocktail,
        iconUnselected = R.drawable.icon_cocktail,
        route = Screens.HomeScreen.name
    ),
    NavItems(
        title = "Favorite",
        iconSelected  = R.drawable.icon_favorite,
        iconUnselected = R.drawable.icon_favorite,
        route = Screens.FavoritCocktailScreen.name
    ),
    NavItems(
        title = "Random",
        iconSelected  = R.drawable.cube3,
        iconUnselected = R.drawable.cube3,
        route = Screens.RandomCocktailScreen.name
    ),
)