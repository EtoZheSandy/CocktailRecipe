package su.afk.cocktailrecipe.presentation.screen.detailScreen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.afk.cocktailrecipe.domain.model.DrinkDetail


@Composable
fun RecipeDrink(
    ingredientsDrink: DrinkDetail,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxHeight()

            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = ingredientsDrink.recipe,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            modifier = Modifier.padding(10.dp)
        )


        ingredientsDrink.ingredientList.forEachIndexed { index, _ ->
            val ingredient = ingredientsDrink.ingredientList.getOrNull(index) ?: ""
            val measure = ingredientsDrink.measureList.getOrNull(index) ?: ""
            OneIngredient(ingredient = ingredient, measure = measure)
        }

        Spacer(modifier = Modifier.height(150.dp))
    }

}