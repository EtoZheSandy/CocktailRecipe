package su.afk.cocktailrecipe.presentation.screen.homeScreen.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import su.afk.cocktailrecipe.R
import su.afk.cocktailrecipe.presentation.navigation.Screens
import su.afk.cocktailrecipe.presentation.screen.component.LazyDrinkItem
import su.afk.cocktailrecipe.presentation.screen.component.RetrySection
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeScreenEvent
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeScreenState
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeViewModel
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeViewModel.Companion.ERROR_CODE_101
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeViewModel.Companion.ERROR_CODE_102


@Composable
fun HomeDrinkList(
    onNavigateToScreen: (String) -> Unit = {},
    state: HomeScreenState = HomeScreenState(),
    onEvent: (HomeScreenEvent) -> Unit = {},
) {
    val context = LocalContext.current

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp), // content padding
    ) {
        items(state.cocktailList.size) { index ->
            LazyDrinkItem(cocktail = state.cocktailList[index], onNavigateToScreen = onNavigateToScreen)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(50.dp)
            )
        }
        if (state.loadErrorMessage.isNotEmpty()) {
            var errorLanguageText = state.loadErrorMessage

            if (state.loadErrorMessage == ERROR_CODE_101.toString()) {
                errorLanguageText = context.getString(R.string.unknown_error)
            } else if (state.loadErrorMessage == ERROR_CODE_102.toString()) {
                errorLanguageText = context.getString(R.string.network_error)
            }

            RetrySection(error = errorLanguageText) {
                onEvent(HomeScreenEvent.Retry)
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeDrinkListPreviewError() {
    HomeDrinkList(state = HomeScreenState(loadErrorMessage = "Сетевая ошибка"))
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun HomeDrinkListPreviewLoading() {
    HomeDrinkList(state = HomeScreenState(isLoading = true))
}