package su.afk.cocktailrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import su.afk.cocktailrecipe.navigation.AppNavigation
import su.afk.cocktailrecipe.screen.homeScreen.HomeListViewModel
import su.afk.cocktailrecipe.ui.theme.CocktailRecipeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val viewModel: HomeListViewModel by viewModels()
    private val viewModel by viewModels<HomeListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailRecipeTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}

