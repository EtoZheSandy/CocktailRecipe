package su.afk.cocktailrecipe.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import su.afk.cocktailrecipe.screen.detailCoctail.ScreenDetailCocktail
import su.afk.cocktailrecipe.screen.homepage.ScreenHome

@Composable
fun AppNavigation(navController: NavHostController) {
//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                var selectedItemIndex by rememberSaveable {
//                    mutableStateOf(0)
//                }
//
//                BottomNavigationItems.forEachIndexed { index, screen ->
//                    NavigationBarItem(
//                        selected = selectedItemIndex == index,
//                        onClick = {
//                            selectedItemIndex = index
//                            navController.navigate(screen.route)
//                        },
//                        icon = { Icon(imageVector = screen.icon, contentDescription = screen.title) },
//                        alwaysShowLabel = false,
//                        label = {
//                            Text(text = screen.title)
//                        }
//                    )
//
//                }
//            }
//        }
//    ) {paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
//            modifier = Modifier
//                .padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name) {
                ScreenHome(navController = navController)
            }
            composable(route = Screens.DetailScreen.name + "/{colorDominate}/{idDrink}",
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
//}