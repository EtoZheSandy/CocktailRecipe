package su.afk.cocktailrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import su.afk.cocktailrecipe.navigation.AppNavigation
import su.afk.cocktailrecipe.screen.detailCoctail.ScreenDetailCocktail
import su.afk.cocktailrecipe.screen.homepage.ScreenHome
import su.afk.cocktailrecipe.ui.theme.CocktailRecipeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val viewModel: HomeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailRecipeTheme {
//                AppNavigation()

                // Навигация
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "cocktail_list_screen"
                ) {
                    composable("cocktail_list_screen") {
                        ScreenHome(navController = navController)
                    }

                    composable("cocktail_detail_screen/{colorDominate}/{idDrink}",
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
    }
}

