package su.afk.cocktailrecipe.presentation.screen.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import su.afk.cocktailrecipe.presentation.screen.detailScreen.component.DrinkDetailFavoriteSection
import su.afk.cocktailrecipe.presentation.screen.detailScreen.component.DrinkDetailStateWrapper
import su.afk.cocktailrecipe.presentation.screen.detailScreen.component.DrinkDetailTopSection
import su.afk.cocktailrecipe.util.Resource


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScreenDetailCocktail(
    dominantColor: Color,
    drinkId: String,
    navController: NavController,
    topPadding: Dp = 70.dp,
    drinkImageSize: Dp = 300.dp,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val cocktailDetail by viewModel.cocktailDetail.collectAsState()

    LaunchedEffect(drinkId) {
        viewModel.getDrinkDetail(drinkId)
    }

    LaunchedEffect(cocktailDetail) {
        if (cocktailDetail is Resource.Success) {
            viewModel.checkFavoriteDrink(cocktailDetail.data?.idDrink)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 0.dp)
    ) {
        DrinkDetailTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        DrinkDetailFavoriteSection(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter),
            viewModel = viewModel,
            cocktailDetail = cocktailDetail.data,
        )
        DrinkDetailStateWrapper(
            cocktailDetail = cocktailDetail,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + drinkImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
                .align(Alignment.BottomCenter),
            drinkId = drinkId,
            viewModel = viewModel,
            loadingModifier = Modifier
                .size(100.dp)
                .align(Alignment.Center)
                .padding(
                    top = topPadding + drinkImageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (cocktailDetail is Resource.Success) {
                cocktailDetail.data?.urlDrink?.let {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(it)
                            .crossfade(true)
                            .build(),
                        contentDescription = it,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(drinkImageSize)
                            .offset(y = topPadding)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}