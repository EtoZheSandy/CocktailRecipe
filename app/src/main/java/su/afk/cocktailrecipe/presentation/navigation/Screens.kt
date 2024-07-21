package su.afk.cocktailrecipe.presentation.navigation

import su.afk.cocktailrecipe.R


enum class Screens
{
    HomeScreen,
    DetailCocktailScreen,
    FavoriteCocktailScreen,
    RandomCocktailScreen
}

enum class BottomNavigationItems(
    val title: String,
    val icon: Int
) {
    HomeScreen("Home", R.drawable.icon_home),
    FavoriteCocktailScreen("Favorite", R.drawable.icon_cocktail),
    RandomCocktailScreen("Random", R.drawable.cube3)
}
