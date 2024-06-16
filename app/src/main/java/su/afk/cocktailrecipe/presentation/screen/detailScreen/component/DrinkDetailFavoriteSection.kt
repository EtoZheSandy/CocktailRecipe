package su.afk.cocktailrecipe.presentation.screen.detailScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import su.afk.cocktailrecipe.domain.model.DrinkDetail
import su.afk.cocktailrecipe.presentation.screen.detailScreen.DetailViewModel

@Composable
fun DrinkDetailFavoriteSection(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    cocktailDetail: DrinkDetail?,
) {
    val isFavorite by viewModel.favorite.collectAsState()

    Box(
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            ),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            onClick = {
                if (isFavorite) viewModel.deleteCocktail(cocktailDetail!!) else viewModel.saveCocktail(
                    cocktailDetail!!
                )
            },
            modifier = Modifier
                .size(36.dp)
                .offset(x = -16.dp, y = 24.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}