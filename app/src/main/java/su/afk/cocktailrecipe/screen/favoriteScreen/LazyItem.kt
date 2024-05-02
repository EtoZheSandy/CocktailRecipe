package su.afk.cocktailrecipe.screen.favoriteScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import su.afk.cocktailrecipe.data.models.DrinkListEntry
import su.afk.cocktailrecipe.navigation.Screens

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LazyItem(cocktail: DrinkListEntry, // каждый напиток с названием и id
             navController: NavController, // для навигации по id
             modifier: Modifier = Modifier, // дефолтный Modifier
             viewModel: FavoriteListViewModel = hiltViewModel() // hilt передаст его за меня
) {
    val defaultDominantColor =
        MaterialTheme.colorScheme.surface //дефф цвет если не успели высчитать для картиники

    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }
    var isLoading by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(3.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "${Screens.DetailCocktailScreen}/${dominantColor.toArgb()}/${cocktail.idDrink}"
                )
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(cocktail.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = cocktail.nameDrink,
                onSuccess = {
                    viewModel.calcDominateColor(it.result.drawable) { color ->
                        dominantColor = color
                    }
                    isLoading =
                        false // Устанавливаем флаг загрузки в false после успешной загрузки изображения
                },
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    //                    .fillMaxWidth()
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 14.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Text(
            text = cocktail.nameDrink,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
                .align(BottomCenter),
            maxLines = 1 // Максимальное количество строк
        )
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Center)
            )
        }
    }
}
