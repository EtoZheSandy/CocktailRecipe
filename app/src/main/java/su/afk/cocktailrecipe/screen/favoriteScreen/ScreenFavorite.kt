package su.afk.cocktailrecipe.screen.favoriteScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import su.afk.cocktailrecipe.R


@Composable
fun ScreenFavorite(
    navController: NavController,
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
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
            DrinkFavoriteList(navController = navController)
        }}
    }
}

@Composable
fun DrinkFavoriteList(
    navController: NavController,
    viewModel: FavoriteListViewModel = hiltViewModel()
) {

    val cocktailList by viewModel.favoriteListCocktail.collectAsState(initial = emptyList())

    if(cocktailList.isEmpty()) {
        NoItem()
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
    ) {
        items(cocktailList.size) { index ->
            LazyItem(cocktail = cocktailList[index], navController = navController)
        }
    }
}

@Composable
fun NoItem(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Center
    ) {
        Text(
            text = stringResource(R.string.favorite_not_item),
            textAlign = TextAlign.Center
        )
    }
}