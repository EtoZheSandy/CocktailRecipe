package su.afk.cocktailrecipe.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import su.afk.cocktailrecipe.presentation.navigation.AppNavigation
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeViewModel
import su.afk.cocktailrecipe.presentation.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController, viewModel = viewModel)
            }
        }
    }
}

