package su.afk.cocktailrecipe.presentation.screen.homeScreen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeScreenEvent
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeScreenState
import su.afk.cocktailrecipe.presentation.screen.homeScreen.HomeViewModel

@Composable()
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onEvent: (HomeScreenEvent) -> Unit,
    homeState: HomeScreenState,
) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .shadow(5.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .padding(horizontal = 5.dp, vertical = 0.dp),
                value = homeState.searchInput,
                onValueChange = {
                    onEvent(HomeScreenEvent.Search(it))
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                label = { Text(hint) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable()
fun SearchBarPreview() {
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        hint = "Search",
        onEvent = {},
        homeState = HomeScreenState()
    )
}