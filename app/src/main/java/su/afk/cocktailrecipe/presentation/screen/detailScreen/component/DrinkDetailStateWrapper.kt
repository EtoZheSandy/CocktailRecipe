package su.afk.cocktailrecipe.presentation.screen.detailScreen.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import su.afk.cocktailrecipe.domain.model.DrinkDetail
import su.afk.cocktailrecipe.presentation.screen.component.RetrySection
import su.afk.cocktailrecipe.presentation.screen.detailScreen.DetailViewModel
import su.afk.cocktailrecipe.util.Resource

@Composable
fun DrinkDetailStateWrapper(
    cocktailDetail: Resource<DrinkDetail>,
    modifier: Modifier = Modifier,
    loadingModifier: Modifier = Modifier,
    drinkId: String,
    viewModel: DetailViewModel,
) {
    when (cocktailDetail) {
        is Resource.Success -> {
            DrinkDetailSection(
                cocktailDetail = cocktailDetail.data!!,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }

        is Resource.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                RetrySection(
                    error = cocktailDetail.message!!,
                    onRetry = {
                        viewModel.getDrinkDetail(drinkId)
                    }
                )
            }
        }

        is Resource.Loading -> {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = loadingModifier
            )
        }

    }
}