package su.afk.cocktailrecipe.presentation.screen.favoriteScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import su.afk.cocktailrecipe.R
import su.afk.cocktailrecipe.presentation.screen.component.LazyDrinkItem
import su.afk.cocktailrecipe.presentation.screen.favoriteScreen.component.NoItem


@Composable
fun ScreenFavorite(
    onNavigateToScreen: (String) -> Unit = {},
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier,
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.logo), contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            DrinkFavoriteList(onNavigateToScreen = onNavigateToScreen)
        }}
    }
}

@Composable
fun DrinkFavoriteList(
    onNavigateToScreen: (String) -> Unit = {},
    viewModel: FavoriteListViewModel = hiltViewModel()
) {
    val favoriteCocktailList by viewModel.favoriteListCocktail.collectAsState(initial = emptyList())

    if(favoriteCocktailList.isEmpty()) {
        NoItem()
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(favoriteCocktailList.size) { index ->
            LazyDrinkItem(cocktail = favoriteCocktailList[index], onNavigateToScreen = onNavigateToScreen)
//            LazyDrinkItem(cocktail = favoriteCocktailList[index], navController = navController)
        }
    }
}
