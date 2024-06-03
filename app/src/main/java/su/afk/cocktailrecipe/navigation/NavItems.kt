package su.afk.cocktailrecipe.navigation

import su.afk.cocktailrecipe.R

// более не используется ибо перенес логику в BottomNavigationItemsNew
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
        route = Screens.FavoriteCocktailScreen.name
    ),
    NavItems(
        title = "Random",
        iconSelected  = R.drawable.cube3,
        iconUnselected = R.drawable.cube3,
        route = Screens.RandomCocktailScreen.name
    ),
)