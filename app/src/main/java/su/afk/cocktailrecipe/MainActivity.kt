package su.afk.cocktailrecipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import su.afk.cocktailrecipe.navigation.AppNavigation
import su.afk.cocktailrecipe.ui.theme.CocktailRecipeTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private val viewModel: HomeListViewModel by viewModels()
//    private val viewModel = hiltViewModel<HomeListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailRecipeTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}

