package su.afk.cocktailrecipe.navigation

import su.afk.cocktailrecipe.R

// Все навигационные маршруты
enum class Screens {
    HomeScreen,
    DetailCocktailScreen,
    FavoriteCocktailScreen,
    RandomCocktailScreen
}

// Для bottom навигации
enum class BottomNavigationItemsNew(
    val title: String,
    val icon: Int,
    val route: String
) {
    HomeScreen("Home", R.drawable.icon_home, Screens.HomeScreen.name),
    FavoriteCocktailScreen("Favorite", R.drawable.icon_cocktail, Screens.FavoriteCocktailScreen.name),
    RandomCocktailScreen("Random", R.drawable.cube3, Screens.RandomCocktailScreen.name)
}