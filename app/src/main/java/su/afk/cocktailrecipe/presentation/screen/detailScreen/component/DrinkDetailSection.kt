package su.afk.cocktailrecipe.presentation.screen.detailScreen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.afk.cocktailrecipe.data.network.dto.DrinkResponseDto
import su.afk.cocktailrecipe.domain.model.DrinkDetail

@Composable
fun DrinkDetailSection(
    cocktailDetail: DrinkDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 170.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = cocktailDetail.nameDrink,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 22.sp
        )
        DrinkTypeSection(type = cocktailDetail)
        RecipeDrink(
            ingredientsDrink = cocktailDetail
        )
    }
}