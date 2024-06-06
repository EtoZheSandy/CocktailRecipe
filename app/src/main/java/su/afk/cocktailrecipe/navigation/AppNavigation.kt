package su.afk.cocktailrecipe.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import su.afk.cocktailrecipe.screen.detailScreen.ScreenDetailCocktail
import su.afk.cocktailrecipe.screen.favoriteScreen.ScreenFavorite
import su.afk.cocktailrecipe.screen.homeScreen.HomeListViewModel
import su.afk.cocktailrecipe.screen.homeScreen.ScreenHome

@Composable
fun AppNavigation(navController: NavHostController, viewModel: HomeListViewModel) {
    Scaffold(
        bottomBar = {
            NavigationBar(modifier = Modifier.height(56.dp)){
                // для запоминания состояние выбранного экрана
                var selectedItemIndex by rememberSaveable {
                    mutableIntStateOf(0)
                }

                BottomNavigationItemsNew.entries.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = {
                            selectedItemIndex = index
                            navController.navigate(screen.route)
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = screen.icon),
//                                painter = painterResource(id = if (selectedItemIndex == index) screen.iconSelected else screen.iconUnselected),
                                contentDescription = screen.title,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        alwaysShowLabel = false,
//                        label = { Text(text = screen.title) } // текст снизу иконки
                    )

                }

            }
        }
    ) { paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name) {
                ScreenHome(navController = navController, viewModel)
            }
            composable(route = Screens.FavoriteCocktailScreen.name) {
                ScreenFavorite(navController = navController)
            }
            composable(route = Screens.RandomCocktailScreen.name) {
                ScreenDetailCocktail(
                    navController = navController,
                    dominantColor = Color.White,
                    drinkId = "1")
            }
            composable(route = Screens.DetailCocktailScreen.name + "/{colorDominate}/{idDrink}",
                arguments = listOf(
                    navArgument("colorDominate") {
                        type = NavType.IntType
                    },
                    navArgument("idDrink") {
                        type = NavType.StringType
                    }
                )
            ) {
                val colorDominate = remember {
                    val color = it.arguments?.getInt("colorDominate")
                    color?.let { Color(it) } ?: Color.White
                }
                val idDrink = remember {
                    it.arguments?.getString("idDrink")
                }
                ScreenDetailCocktail(
                    dominantColor = colorDominate,
                    drinkId = idDrink ?: "1",
                    navController = navController
                )
            }
        }

    }
}