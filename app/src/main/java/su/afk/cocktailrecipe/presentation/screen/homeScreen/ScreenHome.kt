package su.afk.cocktailrecipe.presentation.screen.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import su.afk.cocktailrecipe.R
import su.afk.cocktailrecipe.domain.model.Drink
import su.afk.cocktailrecipe.presentation.screen.homeScreen.component.HomeDrinkList
import su.afk.cocktailrecipe.presentation.screen.homeScreen.component.SearchBar

@Composable
fun ScreenHome(
    onNavigateToScreen: (String) -> Unit,
    viewModel: HomeViewModel,
) {
    ScreenView(
        state = viewModel.homeState,
        onNavigateToScreen = onNavigateToScreen,
        onEvent = viewModel::onEvent,
    )
}

@Composable
fun ScreenView(
    onNavigateToScreen: (String) -> Unit = {},
    state: HomeScreenState = HomeScreenState(),
    onEvent: (HomeScreenEvent) -> Unit = {},
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
                        .align(Alignment.CenterHorizontally)
                        .height(50.dp)
                )
                SearchBar(
                    hint = stringResource(R.string.search_hint),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onEvent = onEvent,
                    homeState = state
                )
                Spacer(modifier = Modifier.height(16.dp))

                HomeDrinkList(onNavigateToScreen = onNavigateToScreen, state = state, onEvent = onEvent)
            }
        }
    }
}

@Preview()
@Composable
fun ScreenHomePreview() {
    ScreenView(
        onNavigateToScreen = {},
        state = HomeScreenState(
            cocktailList = listOf(
            Drink(1, "Pina Colada", ""),
            Drink(2, "Pina Colada", ""),
            Drink(3, "Pina Colada", ""),
            Drink(4, "Pina Colada", ""),
            Drink(5, "Pina Colada", ""),
            Drink(6, "Pina Colada", ""),
            Drink(7, "Pina Colada", ""),
            )
        ),
        onEvent = {}
    )
}